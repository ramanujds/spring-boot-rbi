package multithreading;

class Downloader implements Runnable{
    String filename;

    public Downloader(String filename) {
        this.filename = filename;
    }

//    void download(){
//        for (int i=0; i<=100; i+=10) {
//            System.out.println("Downloading " + filename + " " + i + "%");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    public void run(){
        for (int i=0; i<=100; i+=10) {
            System.out.println("Downloading " + filename + " " + i + "%");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}

public class MultiThreadingExample {

    public static void main(String[] args) {

        Downloader downloader1 = new Downloader("file1");
        Downloader downloader2 = new Downloader("file2");
        Thread t1 =Thread.ofVirtual().start(downloader1);
        Thread t2 = Thread.ofVirtual().start(downloader2);


        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException ex){

        }


    }

}
