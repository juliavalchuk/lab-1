import java.util.Random;

public class MonteCarloRunnable implements Runnable {
    private int itteration;
    private int a, lx, ly;
    private int k;


    public  MonteCarloRunnable(int n, int a, int lx, int ly)
    {
        itteration = n;
        this.a = a;
        this.lx = lx;
        this.ly = ly;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        float x, y;

        for(int i = 0; i < itteration; ++i)
        {
            x = rnd.nextFloat() * lx + a;
            y = rnd.nextFloat() * ly;
            k += checkFunction(x, y);
        }
    }

    private  int checkFunction(float x, float y)
    {
        if(y <= Math.abs(2* x * x * x - x * x + x + 1))
            return  1;
        return 0;
    }

    public int getK()
    {
        return k;
    }
}
