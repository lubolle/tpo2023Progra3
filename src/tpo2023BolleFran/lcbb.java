package tpo2023BolleFran;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class lcbb {

	  private static int upper;

	  private static class Node {

	    private int index;
	    private int nivel;
//	    private int cost; // Costo de la matriz
	    private int[] centros;//Este array es el que tiene la situacion de los centros
	    private int U;//optimista
	    private int C;//pesimista

	    //Constructor Node
	    public Node(int index, int nivel, int[] centros,int U, int C) {
	      this.index = index;
	      this.nivel = nivel;
//	      this.cost = cost;
	      this.centros = centros;
	      this.U = U;
	      this.C = C;
	    }
//	    
//	    public Node(int index, int cost, int buildCost, int U, int C) {
//	        this.index = index;
//	        this.cost = cost;
//	        this.buildCost = buildCost;
//	        this.U = U;
//	        this.C = C;
//	      }
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
	  
	  public static int[] lccb2(int[][] M, int max) {
		    // Inicializamos la variable upper.
		    upper = Integer.MAX_VALUE;

		    int centrosGlobales [] = new int[M.length];
		    int nivel = 0;
		   
		    // Inicializamos la cola de prioridad con el nodo raíz. Se define que el criterio para desencolar es el costo de C.
		    PriorityQueue<Node> Q = new PriorityQueue<>(Comparator.comparingInt(n -> n.C));

		    //Inicializamos el nodo raiz.
		    Q.add(new Node(0,nivel,centrosGlobales,upper,calcularC(M,centrosGlobales)));

		    // Mientras la cola no esté vacía...
		    while (!Q.isEmpty()) {
		      // Extraemos el nodo con menor costo U.
		      Node n = Q.poll();
		      System.out.println(n.index);

		      // Si el costo del nodo es menor o igual al del mejor nodo encontrado...
		      //Los nodos que tengan un U mayor al upper son descartados automaticamente
		      if (n.U <= upper) {

		    	  
		    	  upper = n.U; //Actualizamos la referencia de upper
		    	  
		    	  if(n.C==n.U) {
		    		  return n.centros;
		    	  }
		    		  
		    	  //Calculamos RedMin y RedMax
		    	  int redMin = calRedMin(M,n.centros,n.nivel);
		    	  int redMax = calRedMax(M,n.centros,n.nivel);
		    	  int costMantenimiento = M[n.nivel][0];
		    	  
		    	  if(costMantenimiento<redMin) {
		    		  //El costo de mantenimiento es menor que red min
		    		  centrosGlobales[n.nivel]=1;
		    		  n.centros[n.nivel]=1;
		    		  nivel= nivel +1;
		    		  Q.add(new Node(n.index +1, nivel,n.centros,calcularU(M,n.centros),calcularC(M,n.centros)));
		    		  System.out.println("Se construyo uno");

		    	  }
		    	  else if(redMin<costMantenimiento && costMantenimiento<redMax) {
		    		  //No se puede podar nada, tengo que seguir analizando, el costo de mantenimiento esta en el medio de las cotas
		    		  
		    		  
		    		  int[] centrosCopia1= Arrays.copyOf(centrosGlobales,centrosGlobales.length);
		    		  centrosCopia1[nivel]=1;
		    		  Q.add(new Node(n.index +1, nivel+1,centrosCopia1,calcularU(M,centrosCopia1),calcularC(M,centrosCopia1)));
		    		  
		    		  int[] centrosCopia2 = Arrays.copyOf(centrosGlobales,centrosGlobales.length);
		    		  centrosCopia2[nivel]=-1;
		    		  Q.add(new Node(n.index +1, nivel+1,centrosCopia2,calcularU(M,centrosCopia2),calcularC(M,centrosCopia2)));

		    	  }
		    	  else if(n.nivel<max) {//Ya se que no lo voy a construir
		    		  centrosGlobales[n.nivel]=-1;
		    		  n.centros[n.nivel]=-1;
		    		  nivel= nivel +1;
		    		  Q.add(new Node(n.index +1, nivel,n.centros,calcularU(M,n.centros),calcularC(M,n.centros)));
		    		  System.out.println("Se rechazo uno");

		    	  }
		    	  
		    	  
		    		  
		    	  
		    	
//		        // ...lo exploramos.
//		        for (int i = 0; i < M.length; i++) {
//		          if (M[n.index][i] > 0) {
//		            // Calculamos el indicador c.
//		            int c = upper - n.U;
//
//		            // Si el indicador c es menor o igual a cero, descartamos la rama.
//		            if (c <= 0) {
//		              continue;
//		            }
//
//		            // Calculamos el upper bound del hijo.
//		            int U = Math.min(n.U + M[n.index][i], upper);
//
//		            // Si el upper bound del hijo es menor que el upper bound global, actualizamos el upper bound global.
//		            if (U < upper) {
//		              upper = U;
//		            }

		            // Generamos los hijos del nodo.
//		            Q.add(new Node(n.index + 1, n.cost + M[n.index][i], n.buildCost + M[n.index][i], U, c));
//		            Q.add(new Node(n.index + 1, n.cost, n.buildCost, U, n.C));

		            // Actualizamos el resultado del nodo.
//		            if (U <= n.C) {
//		              results[n.index] = 1;
//		            } else if (U < upper) {
//		              results[n.index] = 0;
//		            } else {
//		              results[n.index] = -1;
//		            }
		          }
		        }
//		      }
//		    }

		    // Devolvemos el array de resultados.
		    return centrosGlobales;
		  }


	}