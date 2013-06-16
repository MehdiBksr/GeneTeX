package analysis.preprocess;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Vector;

import data.PreprocessedImage;

/** 
 * This basic implementation of a preprocessor only transforms the input image
 * into a <code>PreprocessedImage</code>, i.e. a two-dimensional array of 
 * pixels represented as boolean values, such value being true when the pixel is 
 * coloured, and false when the pixel is at background colour.
 * 
 * @see {@link PreprocessedImage}, {@link Preprocessor}
 * 
 * @author Marceau THALGOTT, Theo MERLE, Mehdi BOUKSARA
 */
public class BasicPreprocessor implements Preprocessor {

	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */

	// To increase sensitivity, raise the threshold


	/** Threshold below which a pixel is considered as colourful (red). */
	private static final int RED_THRESHOLD = 125;
	
	/** Threshold below which a pixel is considered as colourful (green). */
	private static final int GREEN_THRESHOLD = 125;
	
	/** Threshold below which a pixel is considered as colourful (blue). */
	private static final int BLUE_THRESHOLD = 125;

	/* ************************************************************************
	 *                              CONSTRUCTORS   						      *                                                         * 
	 ************************************************************************ */

	public BasicPreprocessor() {}

	/* ************************************************************************
	 *                              METHODS                                   * 
	 ************************************************************************ */

	public PreprocessedImage preprocess(BufferedImage image) {
		BufferedImage rotatedImage = rotate(image, (Math.PI)/4);
		PreprocessedImage preprocessedImage = binarise(rotatedImage);
		int x1 = 0;
		int x2 = 0;
		int y1 = 30;
		int y2 = 0;
		Vector<Point> line = bresenhamLinePoints(x1, y1, x2, y2);
		findLineAngle(x1, y1, x2, y2);
		for (int i = 0; i < line.size(); i++) preprocessedImage.setPixel((int)line.get(i).getX(), (int)line.get(i).getY(), true);
		return preprocessedImage;
	}

	/* ************************************************************************
	 *                          PRIVATE FUNCTIONS                             * 
	 ************************************************************************ */

	/** 
	 * Transforms the given image in form of a BufferedImage into a 
	 * PreprocessedImage containing a binarised version of the initial image.
	 * The binarised image is a two-dimensional array of pixels represented as 
	 * boolean values, such value being true when the pixel is coloured, and 
	 * false when the pixel is at background colour.
	 * 
	 *  @param image The image to be converted.
	 *  
	 *  @return	a structure containing the array of booleans representing the image.
	 */
	private PreprocessedImage binarise(BufferedImage image) {
		boolean[][] bin = new boolean[image.getWidth()][image.getHeight()];

		for (int i = 0; i < bin.length; i++) {
			for (int j = 0; j < bin[0].length; j++) {
				// get RGB components for this pixel
				int rgb = image.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >>  8) & 0xFF;
				int b = (rgb      ) & 0xFF;
				// whether or not this pixel is black (colourful)
				bin[i][j] =  (r < RED_THRESHOLD || g < GREEN_THRESHOLD 
						|| b < BLUE_THRESHOLD);
			}
		}

		return new PreprocessedImage(bin);
	}

	private BufferedImage rotate(BufferedImage image, double angle) {

		int w = image.getWidth();
		int h = image.getHeight();

		BufferedImage rot = new BufferedImage(Math.max(w, h), Math.max(w, h), BufferedImage.TYPE_INT_RGB);
		int rotW = rot.getWidth();
		int rotH = rot.getHeight();
		for (int i = 0; i < rotH; i++) {
			for (int j = 0; j < rotW; j++) { 
				rot.setRGB(j, i,0xFFFFFFFF);
			}
		}
		AffineTransform xform;
		xform = new AffineTransform();
		xform.translate((rot.getWidth() - w)/2, (rot.getHeight() - image.getHeight())/2);
		xform.rotate(angle, w/2, h/2);
		Graphics2D g = (Graphics2D)rot.createGraphics();
		g.drawImage(image, xform, null);
		g.dispose();

		return rot;
	}

	private float findTextRotation(PreprocessedImage preprocessedImage) {
		//TODO To be implemented
		return 0;
	}

	private double findLineAngle(int xFirst, int yFirst, int xLast, int yLast) {
		//TODO To be implemented
		double angle = Math.acos((xLast - xFirst)/Math.hypot((xFirst - xLast), (yFirst - yLast)));
		if (yLast > yFirst) angle *= -1;
		System.out.println("Line orientation = " + angle);
		return angle;
	}

	/** 
	 * Returns a Vector of Point instances containing the ordered list of
	 * points composing a straight line between the point (xFirst, yFirst)
	 * and the point (xLast, yLast).
	 * 
	 * @param x1 the x coordinate of the starting point of the line.
	 * @param y1 the y coordinate of the starting point of the line
	 * @param x2 the x coordinate of the ending point of the line.
	 * @param y2 the y coordinate of the ending point of the line.
	 * @return a Vector of Point containing the ordered list of the points
	 * 		   composing the line.
	 */
	private Vector<Point> bresenhamLinePoints(int x1, int y1, int x2, int y2) {
		//TODO To be implemented
		int dx, dy;
		Vector<Point> res = new Vector<Point>();

		if ((dx = x2 - x1) != 0) {
			if (dx > 0) {
				if ((dy = y2 - y1) != 0) {
					if (dy > 0) {
						if (dx >= dy) {
							int e;
							dx = (e = dx)*2;
							dy *= 2;
							for(;;) {
								res.add(new Point(x1, y1));
								if ((x1 = x1 + 1) == x2) break;
								if ((e -= dy) < 0) {
									y1++;
									e += dx;
								}
							}
						} else {
							int e;
							dy = (e = dy)*2;
							dx *= 2;
							for (;;) {
								res.add(new Point(x1, y1));
								if ((y1 = y1 + 1) == y2) break;
								if ((e -= dx) < 0) {
									x1++;
									e += dy;
								}
							}
						}
					} else {
						if (dx >= -dy) {
							int e;
							dx = (e = dx)*2;
							dy *= 2;
							for (;;) {
								res.add(new Point(x1, y1));
								if ((x1 = x1 + 1) == x2) break;
								if ((e += dy) < 0) {
									y1--;
									e += dx;
								}
							}
						} else {
							int e;
							dy = (e = dy)*2;
							dx *= 2;
							for(;;) {
								res.add(new Point(x1, y1));
								if ((y1 = y1 - 1) == y2) break;
								if ((e += dx) > 0) {
									x1++;
									e += dy;
								}
							}
						}
					}
				} else {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((x1 = x1 + 1) == x2) break;
					}
				}
			} else {
				if ((dy = y2 - y1) != 0) {
					if (dy > 0) {
						if (-dx >= dy) {
							int e;
							dx = (e = dx)*2;
							dy *= 2;
							for(;;) {
								res.add(new Point(x1, y1));
								if ((x1 = x1 - 1) == x2) break;
								if ((e += dy) >= 0) {
									y1++;
									e += dx;
								}
							}
						} else {
							int e;
							dy = (e = dy)*2;
							dx *= 2;
							for(;;) {
								res.add(new Point(x1, y1));
								if ((y1 = y1 + 1) == y2) break;
								if ((e += dx) <= 0) {
									x1--;
									e += dy;
								}
							}
						}
					} else {
						if (dx <= dy) {
							int e;
							dx = (e = dx)*2;
							dy *= 2;
							for(;;) {
								res.add(new Point(x1, y1));
								if ((x1 = x1 - 1) == x2) break;
								if ((e -= dy) >= 0) {
									y1--;
									e += dx;
								}
							}
						} else {
							int e;
							dy = (e = dy)*2;
							dx *= 2;
							for(;;) {
								res.add(new Point(x1, y1));
								if ((y1 = y1 - 1) == y2) break;
								if ((e -= dx) >= 0) {
									x1--;
									e += dy;
								}
							}
						}
					}
				} else {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((x1 = x1 - 1) == x2) break;
					}
				}
			}
		} else {
			if ((dy = y2 - y1) != 0) {
				if (dy > 0) {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((y1 = y1 + 1) == y2) break;
					}
				} else {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((y1 = y1 - 1) == y2) break;
					}
				}
			}
		}
		res.add(new Point(x2, y2));
		return res;
	}
}

