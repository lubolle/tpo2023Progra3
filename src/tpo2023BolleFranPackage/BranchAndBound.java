package tpo2023BolleFranPackage;

class Nodo implements Comparable<Nodo> {
    int nivel;
    int w;
    int c;
    int[] centros;
    double cotaInferior;

    public Nodo(int w, int c, int[] centros, int nivel, double cotaInferior) {
    	
        this.nivel = nivel;
        this.w = w; // Valor de la solucion optimista
        this.c = c; // Valor de la solucion pesimista
        this.centros = centros; // Cuáles centros se construirían (o no) en la solución. EJ [1, -1, -1, 1] 
        this.cotaInferior = cotaInferior;
    }

    @Override
    public int compareTo(Nodo otroNodo) {
        return Double.compare(this.cotaInferior, otroNodo.cotaInferior);
    }
}

public class BranchAndBound {
	
    public static int calcularMaximaReduccion(int[] centros, int[][] centrosClientes) {
        int n = centros.length;
        int m = centrosClientes[0].length; // Número de clientes
        int maxReduccion = 0;
        /*[
      1   [3, 10, 8, 18, 14]
      1   [9,  4, 6,  5,  5]
      -1  [12, 6, 10, 4,  8]
      0   [8, 6, 5, 12, 7]
          ]
         */

		for(int j = 0; j < m; j++) {
			
			int costoMayor = 0;
			int costoConsiderado = Integer.MAX_VALUE;
			int ahorro = 0;
			
			for(int l = 0; l < n; l++) {
				
				if(centros[l] == 1 && costoConsiderado > centrosClientes[l][j] ) {
					costoConsiderado = centrosClientes[l][j];
				}
			}

			System.out.println("Costo considerado => " + costoConsiderado + "(Cliente: " + j + ")");
			
			for(int k = 0; k < n; k++) {
					
				if(centros[k] != 1) {
					
					if(centrosClientes[k][j] > costoMayor) {
						costoMayor = centrosClientes[k][j];
					}
				}
				
			}
			
			System.out.println(costoMayor);
			
			if( costoConsiderado < costoMayor ) {
				ahorro = costoMayor - costoConsiderado;
			}
			maxReduccion += ahorro;
			
		}
        
        return maxReduccion;
    }
    
    public static int calcularMinimaReduccion(int[] centros, int[][] centrosClientes) {
        int n = centros.length;
        int m = centrosClientes[0].length; // Número de clientes
        int minReduccion = 0;
        /*[
          [3, 10, 8, 18, 14]
          [9,  4, 6,  5,  5]
          [12, 6, 10, 4,  8]
          [8, 6, 5, 12, 7]
          ]
         */
        		
		for(int j = 0; j < m; j++) {
			
			int costoMenor = Integer.MAX_VALUE;
			int costoConsiderado = Integer.MAX_VALUE;
			int ahorro = 0;
			
			for(int l = 0; l < n; l++) {
				
				if(centros[l] == 1 && costoConsiderado > centrosClientes[l][j] ) {
					costoConsiderado = centrosClientes[l][j];
				}
			}

			System.out.println("Costo considerado => " + costoConsiderado + "(Cliente: " + j + ")");
			
			for(int k = 0; k < n; k++) {
					
				if(centros[k] != 1) {
					
					if(centrosClientes[k][j] < costoMenor) {
						costoMenor = centrosClientes[k][j];
					}

				}
				
			}
			
			System.out.println(costoMenor);
			
			if( costoConsiderado < costoMenor ) {
				ahorro = costoMenor - costoConsiderado;
			}
			minReduccion += ahorro;
			
		}

        
        return minReduccion;
    }

    
    public static void main(String[] args) {
        // Ejemplo de uso
        int[][] matriz = {
                          {3, 10, 8, 18, 14},
                          {9,  4, 6,  5,  5},
                          {12, 6, 10, 4,  8},
                          {8, 6, 5, 12, 7}
        				};

        int[] centros = {1, 1, -1, 0};
        
        System.out.println("Minima Reduccion => " + calcularMinimaReduccion(centros, matriz));
        //System.out.println("Maxima Reduccion => " + calcularMaximaReduccion(centros, matriz));
        
        //System.out.println("Maxima reducción: " + minimosPorColumna);
        
    }
	
    
}



