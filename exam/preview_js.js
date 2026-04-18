/**
 * Universal Moodle Quiz Viewer Library
 * Handles XML parsing and dynamic rendering in the browser.
 */

class MoodleQuizViewer {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.xmlDoc = null;
        this.currentFilename = "";
    }

    /**
     * Load an XML file from a URL/Path
     */
    async loadFromURL(url) {
        try {
            this.container.innerHTML = '<div id="loading">正在載入試題匯入檔...</div>';
            const response = await fetch(url);
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            const xmlText = await response.text();
            this.currentFilename = url.split('/').pop();
            this.render(xmlText, this.currentFilename);
        } catch (e) {
            console.error('Fetch error:', e);
            let msg = `無法載入檔案: ${url}<br>${e.message}`;
            if (window.location.protocol === 'file:') {
                msg += `<br><br><div style="text-align:left; color: #666; font-size: 0.9em;">
                    ⚠️ 偵測到您正在使用 <code>file://</code> 協定開啟此頁面。
                    由於瀏覽器安全限制 (CORS)，無法直接從下拉選單載入檔案。
                </div>`;
            }
            this.container.innerHTML = `<div class="error">${msg}</div>`;
        }
    }

    /**
     * Load from a File object (FileReader)
     */
    loadFromFile(file) {
        const reader = new FileReader();
        this.currentFilename = file.name;
        this.container.innerHTML = '<div id="loading">正在讀取本地檔案...</div>';
        reader.onload = (e) => {
            this.render(e.target.result, file.name);
        };
        reader.readAsText(file);
    }

    /**
     * Parse and Render XML
     */
    render(xmlString, filename = "") {
        try {
            const parser = new DOMParser();
            this.xmlDoc = parser.parseFromString(xmlString, "text/xml");
            
            // Handle parsing errors
            const parseError = this.xmlDoc.getElementsByTagName("parsererror");
            if (parseError.length > 0) {
                this.container.innerHTML = `<div class="error">XML 解析錯誤！請確認檔案格式是否正確。</div>`;
                return;
            }

            // Update UI info
            const headerInfo = document.getElementById('header-info');
            if (headerInfo) headerInfo.textContent = `預覽檔案: ${filename}`;
            
            const fileNameInput = document.getElementById('exportFilename');
            if (fileNameInput) fileNameInput.value = filename.replace('.xml', '_copy.xml');

            const questions = this.xmlDoc.getElementsByTagName("question");
            const fragment = document.createDocumentFragment();
            
            let categories = [];
            let currentCategory = null;

            for (let i = 0; i < questions.length; i++) {
                try {
                    const q = questions[i];
                    const type = q.getAttribute("type");

                    if (type === "category") {
                        const fullPath = this.getNodeText(q, "category/text") || "新分類";
                        const shortName = fullPath.split('/').pop();
                        const categoryId = `cat-${categories.length}`;
                        
                        currentCategory = {
                            name: shortName,
                            fullName: fullPath,
                            id: categoryId,
                            count: 0
                        };
                        categories.push(currentCategory);

                        const catDiv = document.createElement('div');
                        catDiv.className = 'category-title';
                        catDiv.id = categoryId;
                        catDiv.innerHTML = `📂 分類：${fullPath}`;
                        fragment.appendChild(catDiv);
                    } else {
                        let card = null;
                        if (type === "multichoice") card = this.renderMultichoice(q, i);
                        else if (type === "truefalse") card = this.renderTrueFalse(q, i);
                        else if (type === "essay" || type === "shortanswer") card = this.renderEssay(q, i);
                        
                        if (card) {
                            fragment.appendChild(card);
                            if (currentCategory) currentCategory.count++;
                        }
                    }
                } catch (err) {
                    console.error(`Error rendering question ${i}:`, err);
                    const errDiv = document.createElement('div');
                    errDiv.className = 'error';
                    errDiv.style.padding = '1rem';
                    errDiv.style.fontSize = '0.8em';
                    errDiv.textContent = `⚠️ 無法顯示第 ${i+1} 題 (${err.message})`;
                    fragment.appendChild(errDiv);
                }
            }

            // Deferred Clearing: Only clear the container once we have everything ready to mount
            const wasEmpty = this.container.innerHTML === '' || this.container.querySelector('#empty-state');
            this.container.innerHTML = ''; 
            this.container.appendChild(fragment);

            // Update UI components
            this.updateCategoryNav(categories);
            this.setupLazyLoading();

        } catch (globalErr) {
            console.error('CRITICAL RENDER ERROR:', globalErr);
            this.container.innerHTML = `<div class="error">
                <h3>渲染過程中發生嚴重錯誤</h3>
                <p>${globalErr.message}</p>
                <button onclick="viewer.refresh()" style="margin-top:1rem; padding:0.5rem 1rem; background:var(--primary); color:white; border:none; border-radius:5px;">重新載入</button>
            </div>`;
        }
    }

    /**
     * Manual refresh button logic
     */
    refresh() {
        if (this.xmlDoc) {
            const serializer = new XMLSerializer();
            const xmlString = serializer.serializeToString(this.xmlDoc);
            this.render(xmlString, this.currentFilename);
        } else {
            window.location.reload();
        }
    }

    /**
     * Setup IntersectionObserver for image lazy loading
     */
    setupLazyLoading() {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const img = entry.target;
                    const dataSrc = img.getAttribute('data-src');
                    if (dataSrc) {
                        img.src = dataSrc;
                        img.removeAttribute('data-src');
                        observer.unobserve(img);
                    }
                }
            });
        }, {
            rootMargin: "300px 0px", // Pre-load images 300px before they enter viewport
            threshold: 0.01
        });

        const lazyImages = this.container.querySelectorAll('img[data-src]');
        lazyImages.forEach(img => observer.observe(img));
    }

    /**
     * Update the quick navigation bar at the top
     */
    updateCategoryNav(categories) {
        const nav = document.getElementById('categoryNav');
        if (!nav) return;

        if (categories.length === 0 || (categories.length === 1 && categories[0].count === 0)) {
            nav.classList.remove('visible');
            return;
        }

        const navContent = categories.map(cat => `
            <a href="#${cat.id}" class="nav-item" onclick="event.preventDefault(); document.getElementById('${cat.id}').scrollIntoView({behavior: 'smooth', block: 'start'})">
                ${cat.name} <span class="nav-count">${cat.count}</span>
            </a>
        `).join('');

        nav.innerHTML = navContent;
        nav.classList.add('visible');
    }

    /**
     * Helper to create common question card structure
     */
    createQuestionBase(qNode, index, typeLabel) {
        const fileMap = this.getFileMap(qNode);
        const name = this.getNodeText(qNode, "name/text");
        const qTextNode = qNode.getElementsByTagName("questiontext")[0];
        let qText = this.getNodeText(qTextNode, "text");
        qText = this.processPlaceholders(qText, fileMap);

        const card = document.createElement('div');
        card.className = `question-card q-type-${qNode.getAttribute('type')}`;
        card.dataset.index = index;

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.className = 'q-selector';
        checkbox.dataset.index = index;
        card.appendChild(checkbox);

        const nameDiv = document.createElement('div');
        nameDiv.className = 'q-name';
        nameDiv.textContent = `#${index + 1} [${typeLabel}] - ${name}`;
        card.appendChild(nameDiv);

        const textDiv = document.createElement('div');
        textDiv.className = 'q-text';
        textDiv.innerHTML = qText;
        card.appendChild(textDiv);

        return { card, fileMap };
    }

    renderMultichoice(qNode, index) {
        const { card, fileMap } = this.createQuestionBase(qNode, index, "選擇題");
        const optionsList = document.createElement('ul');
        optionsList.className = 'options';

        const answers = qNode.getElementsByTagName("answer");
        for (let j = 0; j < answers.length; j++) {
            const ans = answers[j];
            const fraction = ans.getAttribute("fraction");
            const isCorrect = fraction !== null && parseFloat(fraction) > 0;
            let ansText = this.getNodeText(ans, "text");
            let feedbackText = this.getNodeText(ans, "feedback/text");

            ansText = this.processPlaceholders(ansText, fileMap);
            feedbackText = this.processPlaceholders(feedbackText, fileMap);

            const li = document.createElement('li');
            li.className = `option ${isCorrect ? 'correct' : ''}`;
            li.innerHTML = ansText;

            if (feedbackText) {
                const fbDiv = document.createElement('div');
                fbDiv.className = 'feedback';
                fbDiv.innerHTML = `💡 回饋：${feedbackText}`;
                li.appendChild(fbDiv);
            }
            optionsList.appendChild(li);
        }

        card.appendChild(optionsList);
        return card;
    }

    renderTrueFalse(qNode, index) {
        const { card, fileMap } = this.createQuestionBase(qNode, index, "是非題");
        const optionsList = document.createElement('ul');
        optionsList.className = 'options';

        const answers = qNode.getElementsByTagName("answer");
        for (let j = 0; j < answers.length; j++) {
            const ans = answers[j];
            const fraction = ans.getAttribute("fraction");
            const isCorrect = fraction === "100";
            let ansText = this.getNodeText(ans, "text");
            const displayLabel = ansText.toLowerCase() === 'true' ? '正確 (True)' : '錯誤 (False)';

            const li = document.createElement('li');
            li.className = `option ${isCorrect ? 'correct' : ''}`;
            li.innerHTML = displayLabel;
            optionsList.appendChild(li);
        }

        card.appendChild(optionsList);
        return card;
    }

    renderEssay(qNode, index) {
        const { card } = this.createQuestionBase(qNode, index, "申論題");
        const essayBox = document.createElement('div');
        essayBox.className = 'feedback';
        essayBox.style.borderLeftColor = '#94a3b8';
        essayBox.innerHTML = `📝 <i>此為開放性簡答/申論題，請於下方區域作答（預覽模式僅供參考）。</i>
        <div style="margin-top:1rem; height:80px; border:1px dashed #cbd5e1; border-radius:8px; background:white;"></div>`;
        card.appendChild(essayBox);
        return card;
    }

    /**
     * Get IDs of selected questions
     */
    getSelectedIndexes() {
        if (!this.container) return [];
        const checkboxes = this.container.querySelectorAll('.q-selector:checked');
        return Array.from(checkboxes).map(cb => parseInt(cb.dataset.index));
    }

    /**
     * Delete selected questions from view (and in-memory XML)
     */
    deleteSelected() {
        const indexes = this.getSelectedIndexes();
        if (indexes.length === 0) {
            alert('請先勾選題目');
            return;
        }

        if (!confirm(`確定要從此預覽中刪除這 ${indexes.length} 個題目嗎？`)) {
            return;
        }

        const questions = this.xmlDoc.getElementsByTagName("question");
        const indexesToRemoveSorted = [...indexes].sort((a, b) => b - a);
        
        for (const idx of indexesToRemoveSorted) {
            const q = questions[idx];
            if (q) q.parentNode.removeChild(q);
        }

        const serializer = new XMLSerializer();
        const xmlString = serializer.serializeToString(this.xmlDoc);
        this.render(xmlString, this.currentFilename);
    }

    /**
     * Export selected or unselected questions to a new XML
     */
    exportXML(exportSelected = true) {
        if (!this.xmlDoc) return;
        
        const selectedIndexes = this.getSelectedIndexes();
        if (exportSelected && selectedIndexes.length === 0) {
            alert('請先勾選要匯出的題目');
            return;
        }

        const newDoc = this.xmlDoc.cloneNode(true);
        const questions = newDoc.getElementsByTagName("question");
        const indexesToRemove = [];

        for (let i = 0; i < questions.length; i++) {
            const q = questions[i];
            const type = q.getAttribute('type');
            if (type === 'category') continue; 

            const isSelected = selectedIndexes.includes(i);
            if (exportSelected && !isSelected) indexesToRemove.push(i);
            if (!exportSelected && isSelected) indexesToRemove.push(i);
        }

        indexesToRemove.sort((a, b) => b - a).forEach(idx => {
            if (questions[idx]) questions[idx].parentNode.removeChild(questions[idx]);
        });

        const customName = document.getElementById('exportFilename').value || 'quiz_export.xml';
        const serializer = new XMLSerializer();
        const xmlString = serializer.serializeToString(newDoc);
        const blob = new Blob([xmlString], { type: 'text/xml' });
        const url = URL.createObjectURL(blob);
        
        const a = document.createElement('a');
        a.href = url;
        a.download = customName.endsWith('.xml') ? customName : customName + '.xml';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }

    /**
     * Helper to get text content from a node path (e.g. "name/text")
     */
    getNodeText(node, path) {
        if (!node) return "";
        const parts = path.split('/');
        let current = node;
        for (const part of parts) {
            current = current.getElementsByTagName(part)[0];
            if (!current) return "";
        }
        
        let text = "";
        for (let k = 0; k < current.childNodes.length; k++) {
            const child = current.childNodes[k];
            if (child.nodeType === 3 || child.nodeType === 4) { // Text or CDATA
                text += child.nodeValue;
            }
        }
        return text.trim();
    }

    /**
     * Create a map of filename to base64 data URI
     */
    getFileMap(qNode) {
        const fileMap = {};
        const files = qNode.getElementsByTagName("file");
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const name = file.getAttribute("name");
            const content = file.textContent.trim();
            const extension = name.split('.').pop().toLowerCase();
            let mimeType = "image/png"; // Default
            if (extension === "jpg" || extension === "jpeg") mimeType = "image/jpeg";
            else if (extension === "gif") mimeType = "image/gif";
            else if (extension === "svg") mimeType = "image/svg+xml";

            fileMap[name] = `data:${mimeType};base64,${content}`;
        }
        return fileMap;
    }

    /**
     * Replace @@PLUGINFILE@@ placeholders with base64 data URIs
     * Now supports Lazy Loading by transforming src into data-src for img tags
     */
    processPlaceholders(html, fileMap) {
        if (!html) return "";
        
        // 1. First handle images specifically to add lazy loading
        let processed = html.replace(/src="@@PLUGINFILE@@\/([^"]+)"/g, (match, filename) => {
            try {
                const decodedName = decodeURIComponent(filename);
                const base64 = fileMap[decodedName] || fileMap[filename];
                if (base64) {
                    // Use a transparent 1x1 pixel as initial src
                    return `src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" data-src="${base64}" loading="lazy" style="min-height: 100px; background: #f1f5f9; display: block; margin: 1rem 0;"`;
                }
            } catch (e) {}
            return match;
        });

        // 2. Fallback for any other @@PLUGINFILE@@ instances
        return processed.replace(/@@PLUGINFILE@@\/([^"' \/>]+)/g, (match, filename) => {
            try {
                const decodedName = decodeURIComponent(filename);
                return fileMap[decodedName] || fileMap[filename] || match;
            } catch (e) {
                return fileMap[filename] || match;
            }
        });
    }
}

// Export for module use or just keep global
window.MoodleQuizViewer = MoodleQuizViewer;
