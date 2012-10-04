import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.nayuki.bmpio.*;


public final class SimpleDemo {
	
	public static void main(String[] args) throws IOException {
		File file = new File("Demo.bmp");
		
		Rgb888Image image = new Rgb888Image() {
			public int getWidth() {
				return 512;
			}
			
			public int getHeight() {
				return 64 + 512;
			}
		
			public int getRgb888Pixel(int x, int y) {
				if (y < 64)
					return (new int[]{0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0x00FFFF, 0xFF00FF, 0xFFFFFF})[x / 64];
				else {
					y -= 64;
					int a = x % 256;
					int b = y % 256;
					if (y < 256) {
						if (x < 256)
							return ((a + b) / 2) * 0x010101;
						else
							return b << 16 | a << 8;
					} else {
						if (x < 256)
							return b << 8 | a << 0;
						else
							return b << 0 | a << 16;
					}
				}
			}
		};
		
		BmpImage bmp = new BmpImage();
		bmp.image = image;
		FileOutputStream out = new FileOutputStream(file);
		BmpWriter.write(out, bmp);
		out.close();
	}
	
}
