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
	/** Preprocesses a BufferedImage, turning it into a PreprocessedImage.
	 * 
	 * @param image The BufferedImage to be preprocessed.
	 * @return The preprocessed image.
	 */
	public PreprocessedImage preprocess(BufferedImage image) {
		PreprocessedImage preprocessedImage = binarise(image);
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

	/** Returns the input BufferedImage rotated of angle radians. Note that
	 * the returned image will be enlarged to a square which size is the
	 * maximum of the input image width and height.
	 * @param image The image to be rotated.
	 * @param angle The angle of the rotation in radians.
	 * @return A BufferedImage rotated of angle radians. The input image is
	 * 		   unchanged.
	 */
	private BufferedImage rotate(BufferedImage image, double angle) {

		int w = image.getWidth();
		int h = image.getHeight();

		/* creating a copy of the image with enough space to store the
		 * rotated image */
		BufferedImage rot = new BufferedImage(Math.max(w, h), Math.max(w, h), BufferedImage.TYPE_INT_RGB);
		int rotW = rot.getWidth();
		int rotH = rot.getHeight();
		// unspecified pixels are black, uncolor them
		for (int i = 0; i < rotH; i++) {
			for (int j = 0; j < rotW; j++) { 
				rot.setRGB(j, i,0xFFFFFFFF);
			}
		}
		AffineTransform xform;
		xform = new AffineTransform();
		// create a translation of the image to the center of the BufferedImage
		xform.translate((rot.getWidth() - w)/2, (rot.getHeight() - image.getHeight())/2);
		/* compose this translation with a rotation of angle radians around
		 * the center of the image */
		xform.rotate(angle, w/2, h/2);
		Graphics2D g = (Graphics2D)rot.createGraphics();
		// draw the image applying the transform
		g.drawImage(image, xform, null);
		g.dispose();

		return rot;
	}

	/**
	 * Finds the angle of rotation necessary to rotate a given image containing
	 * text back to the position where the text is horizontal.
	 * 
	 * @param preprocessedImage The input image.
	 * @return The angle of the rotation needed to put the text back to
	 * 		   horizontal.
	 */
	private float findTextRotation(PreprocessedImage preprocessedImage) {
		//TODO To be implemented.

		return 0;
	}

	/** 
	 * Determines if a segment given by a Vector of Point contains is empty in
	 * a given PreprocessedImage.
	 * 
	 * @param preprocessedImage The input image.
	 * @param line The segment to be checked.
	 * @return true if the segment does not contain any coloured pixel in image,
	 * 		   false if there is at least one coloured pixel in it.
	 */
	private boolean emptyLine(PreprocessedImage preprocessedImage, Vector<Point> line) {
		for (int i = 0; i < line.size(); i++) {
			if (preprocessedImage.getPixels()[(int)line.get(i).getX()][(int)line.get(i).getY()]) return false;
		}
		return true;
	}

	/**
	 * Calculates the trigonometric angle formed by a segment given by its
	 * starting (xFirst, yFirst) and ending (xLast, yLast) points.
	 * 
	 * @param xFirst The x coordinate of the starting point of the segment.
	 * @param yFirst The y coordinate of the starting point of the segment.
	 * @param xLast The x coordinate of the ending point of the segment.
	 * @param yLast The y coordinate of the ending point of the segment.
	 * @return The trigonometric angle formed by the segment in radians, 
	 * between -Pi and Pi.
	 */
	private double findLineAngle(int xFirst, int yFirst, int xLast, int yLast) {
		double angle = Math.acos((xLast - xFirst)/Math.hypot((xFirst - xLast), (yFirst - yLast)));
		// The angle is negative
		if (yLast > yFirst) angle *= -1;
		return angle;
	}

	/** 
	 * Returns a Vector of Point instances containing the ordered list of
	 * points composing a straight line between the point (xFirst, yFirst)
	 * and the point (xLast, yLast), using an optimised version of the well-known
	 * Bresenham algorithm. The terms "quarter" and "octant" refer to the
	 * trigonometric circle. 
	 * 
	 * @param x1 the x coordinate of the starting point of the line.
	 * @param y1 the y coordinate of the starting point of the line
	 * @param x2 the x coordinate of the ending point of the line.
	 * @param y2 the y coordinate of the ending point of the line.
	 * @return a Vector of Point containing the ordered list of the points
	 * 		   composing the line.
	 */
	private Vector<Point> bresenhamLinePoints(int x1, int y1, int x2, int y2) {
		int dx, dy;
		Vector<Point> res = new Vector<Point>();

		if ((dx = x2 - x1) != 0) {
			if (dx > 0) {
				if ((dy = y2 - y1) != 0) {
					// line in the first quarter
					if (dy > 0) {
						if (dx >= dy) {
							// line in the first eighth
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
							// line in the second octant
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
						// line in the fourth quarter
					} else {
						// line in the eigtht octant
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
							// line in the seventh octant
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
					// horizontal line, from left to right
				} else {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((x1 = x1 + 1) == x2) break;
					}
				}
			} else {
				if ((dy = y2 - y1) != 0) {
					if (dy > 0) {
						// line in the fourth octant
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
							// line in the third octant
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
						// line in the third quarter
					} else {
						// line in the fifth octant
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
							// line in the sixth octant
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
					// horizontal line, from right to left
				} else {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((x1 = x1 - 1) == x2) break;
					}
				}
			}
		} else {
			if ((dy = y2 - y1) != 0) {
				// vertical line, from top to bottom
				if (dy > 0) {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((y1 = y1 + 1) == y2) break;
					}
					// vertical line, from bottom to top
				} else {
					for(;;) {
						res.add(new Point(x1, y1));
						if ((y1 = y1 - 1) == y2) break;
					}
				}
			}
		}
		// don't forget to add the ending point
		res.add(new Point(x2, y2));
		return res;
	}
}

