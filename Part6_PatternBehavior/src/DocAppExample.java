abstract class DocApp {
   // TEMPLATE METHOD
   final void openDocument (String name) { 
      if (!canOpenDocument(name)) {
          // cannot handle this document return;
          return;
      }
      Document doc = doCreateDocument(); 
      if (doc != null) {
          docs.addDocument(doc); 
          aboutToOpenDocument(doc); 
          doc.open();
          doc.doRead();
       } 
    }
    abstract boolean canOpenDocument(String name); // PRIMITIVE METHOD
    abstract void aboutToOpenDocument(Document doc); // PRIMITIVE METHOD
    abstract Document doCreateDocument(); // PRIMITIVE METHOD

    // Placeholder classes for compilation
    static class Document { void open(){} void doRead(){} }
    static class Docs { void addDocument(Document d){} }
    Docs docs = new Docs();
}
