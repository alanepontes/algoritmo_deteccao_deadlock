package com.alane.so;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PainelDesenhoProcessos extends Canvas implements Updatable {
	private static final long serialVersionUID = 6640649476884277002L;
	public static final int ALTURA_CIRCULO = 60;
	public static final int LARGURA_CIRCULO = 60;
	public static final int ALTURA_IMAGEM = 70;
	public static final int LARGURA_IMAGEM = 70;
	public static final int CORRECAO_TELA_RECURSO = 25;
	public static final int CORRECAO_TELA_PROCESSO = 25;
	private final int ARR_SIZE = 4;

	
	int quantidadeDeProcessos;
	int quantidadeDeRecursos;
	SistemaOperacional sistemaOperacional;
	ArrayList<String> envolvidosDeadlock;
	ArrayList<Integer> posicaoXProcesso;
	ArrayList<Integer> posicaoYProcesso;
	ArrayList<Integer> posicaoXRecursos;
	ArrayList<Integer> posicaoYRecursos;
	boolean flagRecurso = false;
	boolean flagProcesso = true;
	
	Grafo grafo;
	
	private BufferStrategy bufferStrategy;
	
	public PainelDesenhoProcessos(SistemaOperacional sistemaOperacional,
			int quantidadeDeProcessos, int quantidadeDeRecursos) {
		this.grafo = new Grafo();
		this.envolvidosDeadlock = new ArrayList<String>();
		this.sistemaOperacional = sistemaOperacional;
		this.quantidadeDeProcessos = quantidadeDeProcessos;
		this.quantidadeDeRecursos = quantidadeDeRecursos;
		this.sistemaOperacional.addObservadorDeProcesso(this);
		this.posicaoXProcesso = new ArrayList<Integer>();
		this.posicaoYProcesso = new ArrayList<Integer>();
		this.posicaoXRecursos = new ArrayList<Integer>();
		this.posicaoYRecursos = new ArrayList<Integer>();
		
		setIgnoreRepaint(true);
	}

	public void render(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.YELLOW,
				Color.BLUE, Color.CYAN, Color.WHITE, Color.GRAY, Color.PINK,
				Color.ORANGE, Color.MAGENTA };
		
		Image[] imagensArray = carregandoImagens();
		int metadeDaTelaY = getHeight() / 2;
		int quantidadeDivisoesRecurso = (this.sistemaOperacional
				.getQuantidadeDeRecursos() - 1 );
		int quantidadeDivisoesProcesso = (this.sistemaOperacional
				.getQuantidadeDeProcessos() - 1);
		// int quantidadeDivisoesProcesso =
		// (this.sistemaOperacional.getQuantidadeDeProcessos() - 1);

		int deslocamentoImagemX = getWidth() / (quantidadeDivisoesRecurso + 1);

		int deslocamentoProcessoX = getWidth()
				/ (quantidadeDivisoesProcesso + 1);

		int posicaoProcessosSuperiorY = metadeDaTelaY / 3;
		int posicaoProcessosInferiorY = getHeight() - (metadeDaTelaY / 3);

		/*System.out.println("deslocamentoImagemX " + deslocamentoImagemX);
		System.out
				.println("this.sistemaOperacional.getQuantidadeDeProcessos() "
						+ this.sistemaOperacional.getQuantidadeDeProcessos());*/

      
        for(int i = 0; i <= quantidadeDivisoesRecurso; i++) {
        	posicaoXRecursos.add(deslocamentoImagemX * i
					+ deslocamentoImagemX / 2 - CORRECAO_TELA_RECURSO  + LARGURA_IMAGEM/2);
			
			posicaoYRecursos.add(metadeDaTelaY - ALTURA_IMAGEM / 2);
        }
    
        for(int i = 0; i <= quantidadeDivisoesProcesso; i++) {
        	posicaoXProcesso.add(deslocamentoProcessoX * ((i % 2 == 0 ? i : i - 1))
					+ deslocamentoProcessoX / 2 + CORRECAO_TELA_PROCESSO + LARGURA_CIRCULO/2);
			posicaoYProcesso.add(((i % 2 == 0) ? posicaoProcessosSuperiorY
							: posicaoProcessosInferiorY) - ALTURA_CIRCULO / 2);
        }
        
        // PARA SOLICITACOES
        for(int i = 0; i <= quantidadeDivisoesProcesso; i++) {
			for(int j = 0; j <= quantidadeDivisoesRecurso; j++ ) {
				if(sistemaOperacional.verificarSolicitacao(sistemaOperacional.getProcessos().get(i), sistemaOperacional.getRecursos().get(j))){
					Color previousColor = g.getColor();
					
					if(!(envolvidosDeadlock.contains("P" + i) && envolvidosDeadlock.contains("R" + j))){
						g.setColor(colors[i]);
					}
					
					if(i%2 == 0) {
						drawArrow((Graphics2D)g, posicaoXProcesso.get(i) - 15, posicaoYProcesso.get(i) + ALTURA_CIRCULO - 10,  posicaoXRecursos.get(j), posicaoYRecursos.get(j));
					}
					else {
						drawArrow((Graphics2D)g, posicaoXProcesso.get(i), posicaoYProcesso.get(i), posicaoXRecursos.get(j), posicaoYRecursos.get(j) + ALTURA_IMAGEM);
					}
					
					g.setColor(previousColor);
				}
				
			}
		}
        
        // PARA RECURSOS
        for(int i = 0; i <= quantidadeDivisoesRecurso; i++) {
			for(int j = 0; j <= quantidadeDivisoesProcesso; j++ ) {
				
				if(sistemaOperacional.verificarAlocacao(sistemaOperacional.getProcessos().get(j), sistemaOperacional.getRecursos().get(i))){
					Color previousColor = g.getColor();
					
					if(!(envolvidosDeadlock.contains("P" + j) && envolvidosDeadlock.contains("R" + i))){
						g.setColor(colors[j]);
					}
					
					if(i % 2 == 0 && j % 2 == 0) {
						drawArrow((Graphics2D)g, posicaoXRecursos.get(i), posicaoYRecursos.get(i), posicaoXProcesso.get(j), posicaoYProcesso.get(j) + ALTURA_CIRCULO);
					}
					else if(i % 2 == 0){
						drawArrow((Graphics2D)g, posicaoXRecursos.get(i), posicaoYRecursos.get(i) + ALTURA_IMAGEM, posicaoXProcesso.get(j), posicaoYProcesso.get(j));
					}
					else if (j % 2 == 0) {
						drawArrow((Graphics2D)g, posicaoXRecursos.get(i), posicaoYRecursos.get(i), posicaoXProcesso.get(j), posicaoYProcesso.get(j) + ALTURA_CIRCULO);
					} else {
						drawArrow((Graphics2D)g, posicaoXRecursos.get(i), posicaoYRecursos.get(i) + ALTURA_IMAGEM , posicaoXProcesso.get(j), posicaoYProcesso.get(j));
					}
					
					g.setColor(previousColor);
				}
				
				
			}
        }
        
        
        
		for (int i = 0; i <= quantidadeDivisoesRecurso; i++) {
			g.drawImage(imagensArray[i], deslocamentoImagemX * i
					+ deslocamentoImagemX / 2 - CORRECAO_TELA_RECURSO,
					metadeDaTelaY - ALTURA_IMAGEM / 2, LARGURA_IMAGEM,
					ALTURA_IMAGEM, null);
			
		}
		for (int i = 0; i <= quantidadeDivisoesProcesso; i++) {
			Color previousColor = g.getColor();
			g.setColor(colors[i]);
			g.fillOval(deslocamentoProcessoX * ((i % 2 == 0 ? i : i - 1))
					+ deslocamentoProcessoX / 2 + CORRECAO_TELA_PROCESSO,
					((i % 2 == 0) ? posicaoProcessosSuperiorY
							: posicaoProcessosInferiorY) - ALTURA_CIRCULO / 2,
					LARGURA_CIRCULO, ALTURA_CIRCULO);
			g.setColor(previousColor);
			
			g.drawString("P"+i, deslocamentoProcessoX * ((i % 2 == 0 ? i : i - 1))
					+ deslocamentoProcessoX / 2 + CORRECAO_TELA_PROCESSO,
					((i % 2 == 0) ? posicaoProcessosSuperiorY
							: posicaoProcessosInferiorY) - ALTURA_CIRCULO / 2);
			
		}
		
		
		
		
		// ARESTAS PROCESSO -> RECURSO
		
		
		// g.drawLine(x1, y1, x2, y2);

	}

	  void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
          Graphics2D g = (Graphics2D) g1.create();

          double dx = x2 - x1, dy = y2 - y1;
          double angle = Math.atan2(dy, dx);
          int len = (int) Math.sqrt(dx*dx + dy*dy);
          AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
          at.concatenate(AffineTransform.getRotateInstance(angle));
          g.transform(at);

          // Draw horizontal arrow starting in (0, 0)
          g.drawLine(0, 0, len, 0);
          g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                        new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
      }
	  
	  private void drawArrow( Graphics2D g, int x, int y, int xx, int yy )
	  {
	    float arrowWidth = 15.0f ;
	    float theta = 1f ;
	    int[] xPoints = new int[ 3 ] ;
	    int[] yPoints = new int[ 3 ] ;
	    float[] vecLine = new float[ 2 ] ;
	    float[] vecLeft = new float[ 2 ] ;
	    float fLength;
	    float th;
	    float ta;
	    float baseX, baseY ;

	    xPoints[ 0 ] = xx ;
	    yPoints[ 0 ] = yy ;

	    // build the line vector
	    vecLine[ 0 ] = (float)xPoints[ 0 ] - x ;
	    vecLine[ 1 ] = (float)yPoints[ 0 ] - y ;

	    // build the arrow base vector - normal to the line
	    vecLeft[ 0 ] = -vecLine[ 1 ] ;
	    vecLeft[ 1 ] = vecLine[ 0 ] ;

	    // setup length parameters
	    fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
	    th = arrowWidth / ( 2.0f * fLength ) ;
	    ta = arrowWidth / ( 2.0f * ( (float)Math.tan( theta ) / 2.0f ) * fLength ) ;

	    // find the base of the arrow
	    baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
	    baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

	    // build the points on the sides of the arrow
	    xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
	    yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
	    xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
	    yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );

	    g.drawLine( x, y, (int)baseX, (int)baseY ) ;
	    g.fillPolygon( xPoints, yPoints, 3 ) ;
	  }

	
	public Image[] carregandoImagens() {
		Image[] fileArray = new Image[10];
		try {
			
			fileArray[0] = ImageIO.read(new File(Thread.currentThread().getContextClassLoader()
					.getResource("recursojoystick.jpg")
					.getFile()));
			fileArray[1] = ImageIO.read(new File(PainelDesenhoProcessos.class.getClassLoader()
					.getResource("recursowebcam.jpg")
					.getFile()));
			fileArray[2] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursomicrofone.jpg")
					.getFile()));
			
			
			fileArray[3] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursohd.jpg")
					.getFile()));
			fileArray[4] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursopendrive.jpg")
					.getFile()));
			fileArray[5] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursomouse.jpg")
					.getFile()));
			fileArray[6] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursoteclado.jpg")
					.getFile()));
			fileArray[7] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursofaxmodem.jpg")
					.getFile()));
			fileArray[8] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursodvdcd.jpg")
					.getFile()));
			fileArray[9] = ImageIO.read(new File(getClass().getClassLoader()
					.getResource("recursoimpressora.jpg")
					.getFile()));
			//System.out.println("load" + getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileArray;
	}

	@Override
	public void update() {
		this.grafo = sistemaOperacional.getGrafo();
		this.envolvidosDeadlock = sistemaOperacional.getEnvolvidosDeadlock();
		
		if(bufferStrategy == null){
			init();
		}
		
		Graphics g = bufferStrategy.getDrawGraphics();
		
		try{
			render(g);
			if (!bufferStrategy.contentsLost()) {
				bufferStrategy.show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		g.dispose();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void init(){
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		
	}
}
