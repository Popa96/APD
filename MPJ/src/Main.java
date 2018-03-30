import mpi.*;

public class Main {

    public static void main(String[] args) {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        final int Master = 0;
        int part_sum = 0;
        int low;
        int high;
        int buffer_Master[]=new int[2];
        int buffer_Slave[]=new int [1];
        int sum = 0;

        if (rank == Master) {

            int my_vect[] = {2, 5, 6, 8, 5, 9, 5, 9};
            int length;
            length = my_vect.length / size;

            for(int i=0;i<length;i++) {
                sum += my_vect[i];
            }
            System.out.println("Partial sum-Proces" + rank);
            System.out.println("Partial Sum is" + sum);

            for (int i = 1; i < size; i++) {
                low = i * length;
                high = low + length;
                int j = 0;
                for (int iterator = low; iterator < high; iterator++) {
                    buffer_Master[j] = my_vect[iterator];
                    j++;
                }
                MPI.COMM_WORLD.Send(buffer_Master, 0, length, MPI.INT, i, 0);
            }
        }
            if (rank != Master) {
                MPI.COMM_WORLD.Recv(buffer_Master, 0, 2, MPI.INT, 0, 0);

                for (int k = 0; k < 2; k++) {
                    part_sum += buffer_Master[k];
                }
                System.out.println("Partial sum-Proces" + rank);
                System.out.println("Partial Sum is" + part_sum);
                buffer_Slave[0]=part_sum;
                MPI.COMM_WORLD.Send(buffer_Slave, 0, 1, MPI.INT, Master, 0);

            }
            if (rank == Master) {
                System.out.println("intra in master final");

                for(int i=1;i<size;i++)
                {
                    MPI.COMM_WORLD.Recv(buffer_Slave, 0, 1, MPI.INT, i, 0);
                    sum+=buffer_Slave[0];
                }
                System.out.println("Total sum is:" + sum);
            }

            MPI.Finalize();
        }

}
