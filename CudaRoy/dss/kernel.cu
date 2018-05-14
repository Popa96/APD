
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <iostream>

#define N 5
#define  INF 9999

int matrixGraph[N][N] = {
	{ 0, 5, INF, 3, INF },
	{ INF, 0, 6, INF, 4 },
	{ INF, INF, 0, 9, INF },
	{ INF, INF, INF, 0, INF },
	{ INF, INF, INF, 2, 0 }
};

__global__ void RoyFloyd (int matrixGraph[N][N])
{
	
	int i = threadIdx.x;
	int j = threadIdx.y;
	for (int k = 0; k < N; k++)
	{
		if (matrixGraph[i][k] + matrixGraph[k][j] < matrixGraph[i][j]) {
			matrixGraph[i][j] = matrixGraph[i][k] + matrixGraph[k][j];
		}
	}
}

int main()
{
	int *matrix;

	cudaMalloc(&matrix, N*N * sizeof(int));
	for (int k = 0; k < N; ++k)
	{
		cudaMemcpy(matrix, matrixGraph, N * N * sizeof(int), cudaMemcpyHostToDevice);
		int* d_k;
		cudaMalloc(&d_k, sizeof(int));
		cudaMemcpy(d_k, &k, sizeof(int), cudaMemcpyHostToDevice);
		int numBlocks = 1;
		dim3 threadsPerBlock(N, N);
		RoyFloyd << < numBlocks, threadsPerBlock >> > (matrix);

		cudaMemcpy(matrixGraph, matrix, N * N * sizeof(int), cudaMemcpyDeviceToHost);
	}
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < N; ++j)
		{
			if (matrixGraph[i][j] == INF)
				std::cout << "inf ,";
			else
				std::cout << matrixGraph[i][j] << ", ";
		}
		std::cout << std::endl;
	}

    cudaFree(matrixGraph);
    cudaFree(matrix);
	system("pause");
    return 0;

}
