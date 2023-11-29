package tpo2023BolleFran;import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class lcbb {

	private static int upper;
	private static class Node {

		private int index;
		private int nivel;
		//      private int cost; // Costo de la matriz
		private int[] centros;//Este array es el que tiene la situacion de los centros
		private int U;//optimista
		private int C;//pesimista

		//Constructor Node
		public Node(int index, int nivel, int[] centros, int U, int C) {
			this.index = index;
			this.nivel = nivel;
			//        this.cost = cost;
			this.centros = centros;
			this.U = U;
			this.C = C;
		}

	}

	public static int[] leasCostBB(int[][] M, int max) {
		// Inicializamos la variable upper y el arreglo de centros en 0.
		upper = Integer.MAX_VALUE;
		int centrosInicial [] = new int[M.length];

		// Inicializamos la cola de prioridad con el nodo raíz. Se define que el criterio para desencolar es el costo de C.
		PriorityQueue<Node> Q = new PriorityQueue<>(Comparator.comparingInt(n -> n.C));

		//Inicializamos el nodo raiz, junto con un nodo mejorSolucion donde almacenaremos luego.
		Node mejorSolucion = new Node(0, 0, centrosInicial, upper, calcularC(M, centrosInicial));
		Q.add(mejorSolucion);

		// Mientras la cola no esté vacía...
		while (!Q.isEmpty()) {

			// Extraemos el nodo con menor costo C.
			Node n = Q.poll();

			// Si el costo del nodo es menor o igual al del mejor nodo encontrado...
			//Los nodos que tengan un U mayor al upper son descartados automaticamente
			if (n.U <= upper) {


				upper = n.U; //Actualizamos la referencia de upper

				if(n.nivel < max) {
					int nuevoNivel = n.nivel + 1;

					if(n.C==n.U) {
						//Encontramos un costo
						return n.centros;
					}

					//Calculamos RedMin y RedMax
					int redMin = calRedMin(M,n.centros,n.nivel);
					int redMax = calRedMax(M,n.centros,n.nivel);
					int costMantenimiento = M[n.nivel][0];
					String arrayAsString = Arrays.toString(n.centros);
					System.out.println("Nodo:"+n.index+"|costMant:"+costMantenimiento+"|centros:"+arrayAsString+"|C:"+n.C+"|U:"+n.U+"|RedMax:"+redMax+"|RedMin:"+redMin);

					if(costMantenimiento<redMin) {
						//El costo de mantenimiento es menor que red min
						n.centros[n.nivel]=1;
						Q.add(new Node(n.index +1, nuevoNivel, n.centros,calcularU(M,n.centros),calcularC(M,n.centros)));
					}
					else if(redMin<costMantenimiento && costMantenimiento<redMax) {
						//No se puede podar nada, tengo que seguir analizando, el costo de mantenimiento esta en el medio de las cotas
						int[] centrosCopia1= Arrays.copyOf(n.centros, n.centros.length);
						centrosCopia1[n.nivel]=1;
						Q.add(new Node(n.index + 1, nuevoNivel,centrosCopia1,calcularU(M,centrosCopia1),calcularC(M,centrosCopia1)));

						int[] centrosCopia2 = Arrays.copyOf(n.centros, n.centros.length);
						centrosCopia2[n.nivel]=-1;
						Q.add(new Node(n.index + 2, nuevoNivel, centrosCopia2,calcularU(M,centrosCopia2),calcularC(M,centrosCopia2)));

					}
					else if(n.nivel<max) {//Ya se que no lo voy a construir
						n.centros[n.nivel]=-1;
						Q.add(new Node(n.index +1, nuevoNivel, n.centros,calcularU(M,n.centros),calcularC(M,n.centros)));	    			  
					}
				}else {
					mejorSolucion = n;
				}

			}
		}
		// Devolvemos el array de resultados.
		System.out.println("El costo C es: " + mejorSolucion.C);
		return mejorSolucion.centros;
	}

	public static int calcularU(int[][] M, int[] centros) {
		int c = 0;
		int flagCostosFijos = 0;// Bandera que uso para sumar solamente una vez los costos fijos

		for (int j = 1; j < M[0].length; j++) {//me muevo en columnas
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < centros.length; i++) {// me muevo en filas
				if (centros[i] == 1) {
					//c += M[i][0];//Es un centro construido le guardo el costo fijo
					min = Math.min(min, M[i][j]);
					if (flagCostosFijos < centros.length) {
						c += M[i][0];//Es un centro construido le guardo el costo fijo
					}
				}
				flagCostosFijos++;
			}
			c += min;//sumo el minimo encontrado
		}
		return c;
	}

	public static int calcularC(int[][] M, int[] centros) {
		int c = 0;
		int flagCostosFijos = 0;// Bandera que uso para sumar solamente una vez los costos fijos

		for (int j = 1; j < M[0].length; j++) {//me muevo en columnas
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < centros.length; i++) {// me muevo en filas
				flagCostosFijos++;
				if (centros[i] == 1) {
					min = Math.min(min, M[i][j]);
					if (flagCostosFijos < centros.length) {
						c += M[i][0];//Es un centro construido le guardo el costo fijo
					}
				}
				if (centros[i] == -1)//El centro esta descartado
				{
					continue;
				}
				if (centros[i] == 0) {
					min = Math.min(min, M[i][j]);
				}
			}
			c += min;//sumo el minimo encontrado
		}
		return c;
	}

	public static int calRedMax(int[][] M, int[] centros, int newCenter) {
		int max;
		int aEval;
		int suma = 0;

		for (int i = 1; i < M[0].length; i++) {//me muevo en columnas
			max = Integer.MIN_VALUE;
			aEval = M[newCenter][i];
			for (int j = 0; j < M.length; j++) {//me muevo en las filasif(j!= newCenter && M[j][i]>max && centros[j] != -1) {
				//                      if (j != newCenter && M[j][i] > aEval && centros[j] != -1 ) {
				if (Arrays.stream(centros).reduce(0, Integer::sum) > 0) {
					max = maxConstr(newCenter, i, M, centros);
					break;
					//i++;//Avanzo de col por que no vale la pena continuar analizando
				} else if (j != newCenter && M[j][i] > max && centros[j] != -1) {
					max = M[j][i];
				}
			}
			if (max - aEval > 0) {
				suma += (max - aEval);
			}
			//                     else
			//                        suma +=max;
			//              }
		}
		return suma;
	}

	public static int maxConstr(int newCenter, int columna, int[][] M, int[] construidos) {
		int valorActual = M[newCenter][columna];
		int ultimoActual = 9999;
		// Verificar si el valor actual es el mínimo en la columna
		for (int i = 0; i < M.length; i++) {

			if (construidos[i] == -1) {
				continue;
			}
			if (construidos[i] == 1 && M[i][columna] < valorActual) {
				return 0; // Existe un valor menor en la columna
			} else if (M[i][columna] < ultimoActual && construidos[i] == 1) {
				ultimoActual = M[i][columna];
			}
		}

		// Si no se encontró un valor menor, entonces es el mínimo en la columna
		return ultimoActual;
	}

	public static int calRedMin(int[][] M, int[] centros, int newCenter) {
		int min;
		int min2;
		int suma = 0;

		for (int i = 1; i < M[0].length; i++) {//me muevo en columnas
			if (esMinimo(newCenter, i, M, centros)) {
				min = M[newCenter][i];
				min2 = Integer.MAX_VALUE;
				for (int j = 0; j < M.length; j++) {//me muevo en las filas
					if (j != newCenter && M[j][i] < min2 && centros[j] != -1) {
						min2 = M[j][i];
					}
				}
				suma += (min2 - min);
			}
		}
		return suma;
	}

	public static boolean esMinimo(int construido, int columna, int[][] costos, int[] construidos) {
		int valorActual = costos[construido][columna];

		// Verificar si el valor actual es el mínimo en la columna
		for (int i = 0; i < costos.length; i++) {

			if (construidos[i] == -1) {
				continue;
			}
			if (costos[i][columna] < valorActual) {
				return false; // Existe un valor menor en la columna
			}
		}
		// Si no se encontró un valor menor, entonces es el mínimo en la columna
		return true;
	}


}