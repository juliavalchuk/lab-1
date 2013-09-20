import java.util.Date;

public class ParallelMonteCarlo {

    public static void main(String args[])
    {
        try{
              if(args.length == 3)
                   run(Integer.parseInt(args[0]),Integer.parseInt(args[1]), Integer.parseInt(args[2])) ;
        } catch (Exception ex)
        {
            System.out.println("Wrong arguments");
        }


    }

    private static void run(int N, int a, int b)
    {
        int itteration = 1000000000, k = 0;
        float integral;
        int y = Math.max(func(a), func(b));
        MonteCarloRunnable[] runnables = new MonteCarloRunnable[N];
        Thread[] threads = new Thread[N];

        Date startTime = new Date();

        for(int i = 0; i < N; ++i)
        {
            runnables[i] = new MonteCarloRunnable(itteration / N, a, b - a, y);
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
        }
        for(int i = 0; i < N; ++i)
        {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            k += runnables[i].getK();
        }

        Date finishTime = new Date();

        itteration = itteration / N * N;
        integral =  (b - a) * y * 1.f / itteration * k;

        output(a, b, integral, N, itteration, finishTime.getTime() - startTime.getTime());
    }

    private static void output(int a, int b, float integral, int N, int iter, long time)
    {
        System.out.println("INTEGRAL [" + a + ", " + b + "] is " + integral);
        System.out.println("THREADS " + N);
        System.out.println("ITERATIONS " + iter);
        System.out.println("TIME " + time + "ms");
    }

    private static int func(int x)
    {
        return (int) Math.abs(2* Math.pow(x, 3) - Math.pow(x, 2) + x + 1);
    }
 }
