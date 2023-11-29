package tpo2023BolleFran;

public class costoFinalCliente {

	public static int[] costoFinal(int[][] M, int[] centros) {

//		  int[][]Mparcial = {
//		    		{2300, 540, 510, 420, 360, 310, 250, 380, 300, 390, 320, 260, 270, 240, 180, 160, 210, 190, 240, 130, 140, 500, 360, 340, 310, 360, 280, 320, 380, 450, 450, 360, 330, 250, 180, 390, 340, 310, 240, 330, 170,  90, 120, 390, 560, 240, 270, 300, 250,  90,  70}, 
//		    		{1900, 320, 530, 440, 500, 550, 500, 580, 600, 690, 620, 560, 630, 540, 600, 590, 640, 680, 730, 680, 690, 110, 250, 270, 300, 370, 420, 460, 520, 590, 620, 530, 500, 500, 430, 600, 550, 640, 600, 690, 660, 580, 490, 220, 220, 380, 340, 540, 610, 520, 620},
//		    		{1500, 300, 510, 420, 480, 530, 510, 560, 610, 700, 630, 570, 640, 550, 610, 600, 650, 690, 740, 690, 700, 210, 260, 280, 310, 380, 430, 470, 530, 600, 630, 540, 510, 510, 440, 610, 560, 650, 610, 700, 670, 590, 500, 230, 200, 390, 350, 550, 620, 530, 630},
//		    		{2000, 190, 400, 310, 370, 420, 400, 450, 500, 590, 520, 460, 530, 440, 500, 490, 540, 580, 630, 580, 590, 220, 150, 170, 200, 270, 320, 360, 420, 490, 520, 430, 400, 400, 330, 500, 450, 540, 500, 590, 560, 480, 390, 120,  90, 280, 240, 440, 510, 420, 520},
//		    		{2700, 210, 420, 330, 390, 440, 480, 470, 550, 640, 600, 540, 610, 520, 580, 570, 620, 660, 710, 660, 670, 300, 230, 250, 280, 350, 400, 440, 500, 570, 600, 510, 480, 480, 410, 580, 530, 620, 580, 670, 640, 560, 470, 200, 110, 360, 320, 520, 590, 500, 600},
//		    		{2500, 150, 360, 270, 330, 380, 440, 410, 490, 580, 560, 500, 570, 480, 540, 530, 580, 620, 670, 620, 630, 260, 190, 210, 240, 310, 360, 400, 460, 530, 560, 470, 440, 440, 370, 540, 490, 580, 540, 630, 600, 520, 430, 160,  50, 320, 280, 480, 550, 460, 560},
//		    		{3000, 130, 340, 250, 310, 360, 420, 390, 470, 560, 540, 480, 550, 460, 520, 610, 570, 610, 660, 670, 680, 340, 270, 290, 320, 390, 440, 480, 540, 610, 640, 550, 520, 520, 450, 620, 570, 660, 620, 710, 680, 600, 510, 240, 130, 400, 360, 560, 530, 540, 640},
//		    		{500 , 50 , 260, 170, 230, 280, 340, 310, 390, 480, 460, 400, 470, 380, 440, 540, 490, 530, 580, 590, 600, 420, 350, 370, 400, 470, 510, 550, 610, 680, 680, 590, 560, 480, 410, 620, 570, 660, 590, 680, 640, 560, 470, 320, 150, 470, 440, 530, 450, 500, 600}};
//		  
		int cantProved = M.length;
		int cantCentros = M[0].length;
		int costos[] = new int[cantCentros-1];
		for(int i = 1; i < cantCentros; i++) {
			int menorCosto = Integer.MAX_VALUE;
			for(int j = 0; j < cantProved; j++) {
				if(M[j][i] < menorCosto && centros[j] == 1) {
					menorCosto = M[j][i];
					costos[i-1] = M[j][i];
				}
			}
		}
		return costos;
	}

}
