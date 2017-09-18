import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class ImageManager {
	
	private ImageManager() {}
	//
	// sprites
	public static final BufferedImage BLACK_PAWN;
	public static final BufferedImage WHITE_PAWN;
	public static final BufferedImage BLACK_ROOK;
	public static final BufferedImage WHITE_ROOK;
	public static final BufferedImage BLACK_KNIGHT;
	public static final BufferedImage WHITE_KNIGHT;
	public static final BufferedImage BLACK_BISHOP;
	public static final BufferedImage WHITE_BISHOP;
	public static final BufferedImage BLACK_QUEEN;
	public static final BufferedImage WHITE_QUEEN;
	public static final BufferedImage BLACK_KING;
	public static final BufferedImage WHITE_KING;
	
	static {
		// Stored images are 60px*60px
		BufferedImage bp = null;
		BufferedImage br = null;
		BufferedImage bn = null;
		BufferedImage bb = null;
		BufferedImage bq = null;
		BufferedImage bk = null;
		BufferedImage wp = null;
		BufferedImage wr = null;
		BufferedImage wn = null;
		BufferedImage wb = null;
		BufferedImage wq = null;
		BufferedImage wk = null;
		
		try {
			bp = ImageIO.read(new File("img/black_pawn.png"));
			br = ImageIO.read(new File("img/black_rook.png"));
			bn = ImageIO.read(new File("img/black_knight.png"));
			bb = ImageIO.read(new File("img/black_bishop.png"));
			bq = ImageIO.read(new File("img/black_queen.png"));
			bk = ImageIO.read(new File("img/black_king.png"));
			wp = ImageIO.read(new File("img/white_pawn.png"));
			wr = ImageIO.read(new File("img/white_rook.png"));
			wn = ImageIO.read(new File("img/white_knight.png"));
			wb = ImageIO.read(new File("img/white_bishop.png"));
			wq = ImageIO.read(new File("img/white_queen.png"));
			wk = ImageIO.read(new File("img/white_king.png"));
		} catch (IOException e) {
		}
		
		BLACK_PAWN = bp;
		BLACK_ROOK = br;		
		BLACK_KNIGHT = bn;		
		BLACK_BISHOP = bb;		
		BLACK_QUEEN = bq;		
		BLACK_KING = bk;
		
		WHITE_PAWN = wp;
		WHITE_ROOK = wr;
		WHITE_KNIGHT = wn;
		WHITE_BISHOP = wb;
		WHITE_QUEEN = wq;
		WHITE_KING = wk;
	}
}