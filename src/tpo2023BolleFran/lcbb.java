package tpo2023BolleFran;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class lcbb {

	  private static int bestCost;
	  private static int bestIndex = -1;

	  private static class Node {

	    private int index;
	    private int cost;
	    private int[] built;
	    private int u;//optimista
	    private int c;//pesimista

	    public Node(int index, int cost, int[] built,int u, int c) {
	      this.index = index;
	      this.cost = cost;
	      this.built = built;
	      this.u = u;
	      this.c = c;
	    }
	  }
	  public static int calcularU(int[][] M, int[] centros) {
		  int c = 0;
		  int flagCostosFijos = 0;// Bandera que uso para sumar solamente una vez los costos fijos

		  for(int j = 1; j  < M[0].length; j++) {//me muevo en columnas
			  int min = Integer.MAX_VALUE;
			  for(int i = 0 ; i<centros.length; i ++) {// me muevo en filas
				  if(centros[i]==1) {
					  //c += M[i][0];//Es un centro construido le guardo el costo fijo
					  min = Math.min(min, M[i][j]);
					  if(flagCostosFijos<centros.length) {
						  c += M[i][0];//Es un centro construido le guardo el costo fijo
					  }
				  }
				  
				  flagCostosFijos++;
			  }
			  c +=min;//sumo el minimo encontrado
		  }
		  return c;
	  }
	  
	  public static int calcularC(int[][] M, int[] centros) {
		  int c = 0;
		  int flagCostosFijos = 0;// Bandera que uso para sumar solamente una vez los costos fijos

		  for(int j = 1; j  < M[0].length; j++) {//me muevo en columnas
			  int min = Integer.MAX_VALUE;
			  for(int i = 0 ; i<centros.length; i ++) {// me muevo en filas
				  flagCostosFijos++;
				  if(centros[i]==1) {
					  min = Math.min(min, M[i][j]);
					  if(flagCostosFijos<centros.length) {
						  c += M[i][0];//Es un centro construido le guardo el costo fijo
					  }
				  }
				  if(centros[i]==-1)//El centro esta descartado
					  continue;
				  
				  if(centros[i]==0)
					 min = Math.min(min, M[i][j]);
			  }
			  c +=min;//sumo el minimo encontrado
		  }
		  return c;
	  }
	  
	  public static int calRedMax(int[][] M, int[] centros, int newCenter) {
	        int min;
	        int max;
	        int aEval;
	        int suma = 0;

	        for (int i = 1 ; i < M[0].length ; i++) {//me muevo en columnas
	        	max = Integer.MIN_VALUE;
	        	aEval = M[newCenter][i];
	        	for (int j = 0 ; j < M.length ; j++) {//me muevo en las filasif(j!= newCenter && M[j][i]>max && centros[j] != -1) {
//	                    if (j != newCenter && M[j][i] > aEval && centros[j] != -1 ) {
	                        if(Arrays.stream(centros).reduce(0, Integer::sum)>0) {
	                        	max = maxConstr(newCenter,i,M,centros);
	                        	break;
	                        	//i++;//Avanzo de col por que no vale la pena continuar analizando
	                        }
	                        else if(j != newCenter && M[j][i] > max && centros[j] != -1) {
	                        	max = M[j][i];
	                        	}
	                }
	                if(max-aEval>0)
	                	suma += (max - aEval);
//		                else 
//		                	suma +=max;
//	            }
	        }
	        return suma;
	        }
	  
	  public static int maxConstr(int newCenter, int columna, int[][] M, int[] construidos) {
		    int valorActual = M[newCenter][columna];
		    int ultimoActual=9999;
		    // Verificar si el valor actual es el mínimo en la columna
		    for (int i = 0; i < M.length; i++) {

		    	if(construidos[i]==-1)
		    		continue;
		   	
		        if (construidos[i]==1 && M[i][columna] < valorActual) {
		            return 0; // Existe un valor menor en la columna
		        }
		        else if (M[i][columna]<ultimoActual && construidos[i]==1 )
		        	ultimoActual = M[i][columna];
		    }

		    // Si no se encontró un valor menor, entonces es el mínimo en la columna
		    return ultimoActual;
		}
	  
	  public static int calRedMin(int[][] M, int[] centros, int newCenter) {
	        int min;
	        int min2;
	        int suma = 0;

	        for (int i = 1 ; i < M[0].length ; i++) {//me muevo en columnas
	            if (esMinimo(newCenter, i, M, centros)) {
	                min = M[newCenter][i];
	                min2 = Integer.MAX_VALUE;
	                for (int j = 0 ; j < M.length ; j++) {//me muevo en las filas
	                    if (j != newCenter && M[j][i] < min2 && centros[j] != -1 ) {
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

		    	if(construidos[i]==-1)
		    		continue;
		   	
		        if (costos[i][columna] < valorActual) {
		            return false; // Existe un valor menor en la columna
		        }
		    }

		    // Si no se encontró un valor menor, entonces es el mínimo en la columna
		    return true;
		}
	  
	  public static int branchAndBound(int[][] M, int[] built) {
		// Inicializamos la variable bestCost.
		bestCost = Integer.MAX_VALUE;
	    // Inicializamos la variable bestIndex.
	    bestIndex = -1;
	    // Inicializamos la cola de prioridad con el nodo raíz.
	    //La linea Comparator.comparingInt(n -> n.cost) basicamente es un comparador que compara los obejtos de tipo nodo por su costo.
	    //En otras palabras, esta línea crea una cola de prioridad que siempre extraerá el nodo con el costo más bajo primero.
	    
	    PriorityQueue<Node> Q = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
	    Q.add(new Node(0, M[0][0], built, bestCost,9));

	    // Mientras la cola no esté vacía...
	    while (!Q.isEmpty()) {
	      // Extraemos el nodo con menor costo.
	      Node n = Q.poll();

	      // Si el costo del nodo es menor o igual al del mejor nodo encontrado...
	      if (n.cost <= bestCost) {
	        // ...lo exploramos.
	        for (int i = 0; i < M.length; i++) {
	          if (M[n.index][i] > 0 && built[i] == 0) {
	            // Calculamos el indicador c.
	            int c = bestCost - n.cost;

	            // Si el indicador c es menor o igual a cero, descartamos la rama.
	            if (c <= 0) {
	              continue;
	            }

	            // Generamos los hijos del nodo.
	            int[] newBuilt = built.clone();
	            newBuilt[i] = 1;
//	            Q.add(new Node(n.index + 1, n.cost + M[n.index][i], newBuilt));
//	            Q.add(new Node(n.index + 1, n.cost, newBuilt));
	          }
	        }
	      }
	    }

	    // Devolvemos el índice del mejor nodo encontrado.
	    return bestIndex;
	  }


	}