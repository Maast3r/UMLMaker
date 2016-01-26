package singletons;
// File Name: Singleton.java
public class SingletonEager {

   private static SingletonEager singleton = new SingletonEager( );
   
   /* A private Constructor prevents any other 
    * class from instantiating.
    */
   private SingletonEager(){ }
   
   /* Static 'instance' method */
   public static SingletonEager getInstance( ) {
      return singleton;
   }
   /* Other methods protected by singleton-ness */
   protected static void demoMethod( ) {
      System.out.println("demoMethod for singleton"); 
   }
}