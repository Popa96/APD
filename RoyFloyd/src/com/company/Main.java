package com.company;

import mpi.MPI;

public class Main {
    public static final int NR = 5;
    public static final int INF = 99999;

    public static void main(String[] args) {

        MPI.Init(args);


        int[][] masterGraph = new int[NR][NR];
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int matrixGraph[][] = {
                {0, 5, INF, 3, INF},
                {INF, 0, 6, INF, 4},
                {INF, INF, 0, 9, INF},
                {INF, INF, INF, 0, INF},
                {INF, INF, INF, 2, 0}
        };

        if (rank == 0) {
            MPI.COMM_WORLD.Bcast(matrixGraph, 0,  NR*NR, MPI.INT, 0);

            for (int k = 0; k < NR; k++) {
                for (int j = 0; j < NR; j++) {
                    if (matrixGraph[0][k] + matrixGraph[k][j] < matrixGraph[0][j]) {
                        matrixGraph[0][j] = matrixGraph[0][k] + matrixGraph[k][j];
                    }
                }
            }

            for (int i = 0; i < NR; i++) {
                masterGraph[0][i] = matrixGraph[0][i];
            }

        }
        if (rank != 0) {

            MPI.COMM_WORLD.Recv(matrixGraph, 0, NR, MPI.OBJECT, 0, 0);

            for (int k = 0; k < NR; k++) {
                for (int j = 0; j < NR; j++) {
                    if (matrixGraph[rank][k] + matrixGraph[k][j] < matrixGraph[rank][j]) {
                        matrixGraph[rank][j] = matrixGraph[rank][k] + matrixGraph[k][j];
                    }
                }
            }
            MPI.COMM_WORLD.Alltoall(matrixGraph, 0, NR, MPI.OBJECT, matrixGraph, 0, NR, MPI.OBJECT);
        }
        if (rank == 0) {

            for (int p = 1; p < size; p++) {
                MPI.COMM_WORLD.Recv(matrixGraph, 0, NR, MPI.OBJECT, p, 0);
                for (int j = p; j < matrixGraph.length; j++) {
                    for (int k = 0; k < matrixGraph.length; k++) {
                        masterGraph[j][k] = matrixGraph[j][k];
                    }
                }
            }
        }
        if(rank==0) {
            for (int i = 0; i < NR; i++) {
                for (int j = 0; j < NR; j++) {
                    System.out.print(masterGraph[i][j] + "   ");
                }
            }
        }

        MPI.Finalize();
    }
}

