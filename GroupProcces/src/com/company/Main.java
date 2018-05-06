package com.company;

import mpi.*;

public class Main {

    private static final int NRPROCES= 8;
    public static void main(String[] args) {
	// write your code here;
        MPI.Init(args);
        Group worldGroup=MPI.COMM_WORLD.group;
        Group group1=MPI.GROUP_EMPTY;
        Group group2=MPI.GROUP_EMPTY;
        Comm comm1=MPI.COMM_SELF;
        Comm comm2=MPI.COMM_SELF;

        int v[]={9,1,7,2,6,5,3,8};
        int rank1[]={0,1,2,3};
        int rank2[]={4,5,6,7};
        int recvBuff[]=new int[4];
        int recvBuff1[]=new int[4];

        int rank =MPI.COMM_WORLD.Rank();
        int size =MPI.COMM_WORLD.Size();

        if(size!=NRPROCES)
        {
            System.out.println("Error process");
            MPI.Finalize();
        }


        if(rank<NRPROCES/2)
        {
            group1.Incl(rank1);
        }
        else
        {
            group2.Incl(rank2);
        }
        //Build intra-communicator for local sub-group
        MPI.COMM_WORLD.Create(group1);
        MPI.COMM_WORLD.Create(group2);

//The communicator containing the process that initiates the inter-communication
        if (rank == 0)
        {
            /* Group 1 communicates with group 2. */
            MPI.COMM_WORLD.Create_intercomm(comm1,0,4,1);
        } else if (rank == 4){
            /* Group 2 communicates with group 1. */
            MPI.COMM_WORLD.Create_intercomm(comm2,4,0,1);
        }
        MPI.COMM_WORLD.Reduce(v,0,recvBuff,0,4,MPI.INT,MPI.SUM,0);
        MPI.COMM_WORLD.Reduce(v,4,recvBuff1,4,4,MPI.INT,MPI.SUM,4);

        System.out.println(recvBuff);
        System.out.println(recvBuff1);
        MPI.Finalize();

    }
}
