import java.util.logging.Level;
import java.util.logging.Logger;
public class Timer extends Thread
{
    private GamePanel panel1;
    public Timer(GamePanel panel1)
    {
        this.panel1 = panel1;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            panel1.generatorePezzi();
            while(panel1.cadutaPezzo() == true)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException exception)
                {
                    Logger.getLogger(Timer.class.getName()).log(Level.SEVERE,null,exception);
                }
            }
            if(panel1.pezzoFuoriSfondo() == true)
            {
                break;
            }
            panel1.pezzoNelloSfondo();
            panel1.cancellaLinee();
        }
    }
}