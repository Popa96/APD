package com.company;
import mpi.*;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int buffer[]=new int[2];


        if(rank!=0)
        {
            Random random =new Random();
            int number = random.nextInt(20);
            buffer[0]=rank;
            buffer[1]=number;
            MPI.COMM_WORLD.Send(buffer,0,2,MPI.INT,0,0);
        }
        if(rank==0)
        {
            Random random =new Random();
            int number = random.nextInt(20);
            int value=number;
            int rank_Leader=0;
            for(int i=1;i<size;i++)
            {
                MPI.COMM_WORLD.Recv(buffer,0,2,MPI.INT,i,0);
                if(buffer[1]>value)
                {
                    value=buffer[1];
                    rank_Leader=i;
                }
                else if((buffer[1]==value)&&(i>rank_Leader))
                {
                    rank_Leader=i;
                }
            }
            System.out.println("Leader proces is"+" "+rank_Leader+" "+"with value"+value);
        }
        MPI.Finalize();
    }
}
