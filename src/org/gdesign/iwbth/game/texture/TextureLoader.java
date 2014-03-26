package org.gdesign.iwbth.game.texture;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.PNGDecoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {
	
	public static SpriteSheet getSpriteSheetFromXML(String xml, SpriteSheet spritesheet) throws ParserConfigurationException, SAXException, IOException{
    	int texture = glLoadLinearPNG(xml.replace("xml", "png"));
    	
    	spritesheet.setTexture(texture);
    	
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
		
		dBuilder = dbFactory.newDocumentBuilder();

        Document document = dBuilder.parse(ClassLoader.getSystemResourceAsStream(xml));
        document.normalize();


        NodeList sprites = document.getElementsByTagName("sprite");
        
    	for (int i = 0; i < sprites.getLength(); i++) {
    		 
    		Node node = sprites.item(i);
     
    		if (node.getNodeType() == Node.ELEMENT_NODE) {
     
    			Element sprite = (Element) node;
     
    			int id = Integer.valueOf(sprite.getAttribute("id"));
    			int x  = Integer.valueOf(sprite.getAttribute("x"));
    			int y  = Integer.valueOf(sprite.getAttribute("y"));
    			int w  = Integer.valueOf(sprite.getAttribute("w"));
    			int h  = Integer.valueOf(sprite.getAttribute("h"));
    			
    			spritesheet.addSprite(new Sprite(texture,x,y,w,h,id));
    		}
    	}
    	return spritesheet;	           
	}

	public static int glLoadLinearPNG(String location) {
	    int texture = glGenTextures();
	    glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texture);
	    InputStream in = null;
	    try {
	        in = ClassLoader.getSystemResourceAsStream(location);
	        PNGDecoder decoder = new PNGDecoder(in);
	        ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
	        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.RGBA);
	        buffer.flip();
	        glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	        glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA,
	                GL_UNSIGNED_BYTE, buffer);
	    } catch (FileNotFoundException e) {
	        System.err.println("Texture file could not be found.");
	        return -1;
	    } catch (IOException e) {
	        System.err.print("Failed to load the texture file.");
	        return -1;
	    } finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
	    return texture;
	}

	/**
	 * Loads a PNG texture and puts it in Gl_TEXTURE_2D
	 *
	 * @param location the location of the png image file
	 *
	 * @return the generated texture handle or -1 if there was a loading error
	 */
	public static int glLoadPNG(String location) {
	    int texture = glGenTextures();
	    glBindTexture(GL_TEXTURE_2D, texture);
	    InputStream in = null;
	    try {
	        in = new FileInputStream(location);
	        PNGDecoder decoder = new PNGDecoder(in);
	        ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
	        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.RGBA);
	        buffer.flip();
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA,
	                GL_UNSIGNED_BYTE, buffer);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return -1;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return -1;
	    } finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    glBindTexture(GL_TEXTURE_2D, 0);
	    return texture;
	}
}