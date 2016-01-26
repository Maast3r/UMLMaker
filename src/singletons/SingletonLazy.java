package singletons;
// File Name: Singleton.java
public class SingletonLazy {

   private static SingletonLazy singleton;
   
   /* A private Constructor prevents any other 
    * class from instantiating.
    */
   private SingletonLazy(){ }
   
   /* Static 'instance' method */
   public static SingletonLazy getInstance( ) {
	   if(singleton == null){
		   singleton = new SingletonLazy();
	   }
      return singleton;
   }
   /* Other methods protected by singleton-ness */
   protected static void demoMethod( ) {
      System.out.println("demoMethod for singleton"); 
   }
}