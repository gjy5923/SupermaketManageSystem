package test3;
public class MyThread implements Runnable{
    private int sum;
    private int new_sum;
    private boolean stopMe = true;
    public void stopMe() {
        stopMe = false;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()  {

        sum=UrlDAO.selectCount();
        WebSocket wbs=new WebSocket();
        while(stopMe){
            new_sum=UrlDAO.selectCount();
            if(sum<new_sum){
                System.out.println("change");
                sum=new_sum;
                wbs.onMessages(sum);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
