import controller.SVGController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import model.AnimatorModel;
import model.CartPt;
import model.Color;
import model.Command;
import model.Rectangle;
import model.Shape;
import model.SimpleAnimatorModel;
import model.Size;
import io.AnimationFileReader;
import view.SVGView;
import io.AnimatorModelBuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing an SVGView.
 */
public class SVGViewTest {
  Color red = new Color(255.0D, 0.0D, 0.0D);

  @Test
  public void testSVGViewLocationChange() throws IOException {
    SimpleAnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    SVGView svgView = new SVGView(model, "SVGData.svg");
    SVGController ctrl = new SVGController(svgView, 1.0D);
    Shape square = new Rectangle("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 1000.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    model.addShape(square);
    model.addCommand(square, c);
    ctrl.startProgram();
    assertEquals(Files.readString(Path.of("SVGData.xml")), "<svg width=\"700\"" +
            " height=\"500\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "\n" +
            "<rect id=\"\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(255,0," +
            "0)" +
            "\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"500000.0ms\" attributeName=\"x" +
            "\" from=\"0.0\" to=\"100.0\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"500000.0ms\" attributeName=\"" +
            "y\" from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "</svg>\n");
  }

  @Test
  public void testSVGViewFileWrite() throws IOException {
    SimpleAnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    SVGView svgView = new SVGView(model, "SVGData.svg");
    SVGController ctrl = new SVGController(svgView, 1.0D);
    Shape square = new Rectangle("", new CartPt(0.0D, 0.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 1000.0D,
            new CartPt(100.0D, 10.0D), new Size(10.0D, 10.0D), this.red);
    model.addShape(square);
    model.addCommand(square, c);
    ctrl.startProgram();
    assertEquals(Files.readString(Path.of("SVGData.xml")),
            "<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
                    "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "\n" +
                    "<rect id=\"\" x=\"0.0\" y=\"0.0\" width=\"10.0\" height=\"10.0\"" +
                    " fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"500000.0ms\" " +
                    "attributeName=\"x\" from=\"0.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"500000.0ms\" " +
                    "attributeName=\"y\" from=\"0.0\" to=\"0.0\" fill=\"freeze\" />\n" +
                    "</rect>\n" +
                    "</svg>\n");

    try {
      assertEquals(Files.readString(Path.of("SVGData.xml")), "<svg width=\"700\" " +
              "height=\"500\" version=\"1.1\"\n" +
              "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
              "\n" +
              "<rect id=\"disk1\" x=\"190.0\" y=\"180.0\" width=\"20.0\" height=\"30.0\" " +
              "fill=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
              "<animate attributeType=\"xml\" begin=\"12500.0ms\" dur=\"5000.0ms\" " +
              "attributeName=\"x\" from=\"190.0\" to=\"190.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"25.0ms\" dur=\"5000.0ms\" attributeName=" +
              "\"y\" from=\"12500.0\" to=\"12500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"18000.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"x\" from=\"190.0\" to=\"490.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"36.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"18000.0\" to=\"18000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"23500.0ms\" dur=\"5000.0ms\" attributeN" +
              "ame=\"x\" from=\"490.0\" to=\"490.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"47.0ms\" dur=\"5000.0ms\" attributeName" +
              "=\"y\" from=\"23500.0\" to=\"23500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"44500.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"x\" from=\"490.0\" to=\"490.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"89.0ms\" dur=\"5000.0ms\" attributeName=\"" +
              "y\"" +
              " from=\"44500.0\" to=\"44500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"50000.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"x\" from=\"490.0\" to=\"340.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"50000.0\" to=\"50000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"55500.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"x\" from=\"340.0\" to=\"340.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"111.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"55500.0\" to=\"55500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"76500.0ms\" dur=\"5000.0ms\" attributeN" +
              "ame=\"x\" from=\"340.0\" to=\"340.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"153.0ms\" dur=\"5000.0ms\" attrib" +
              "uteName=\"" +
              "y\" from=\"76500.0\" to=\"76500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"82000.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"x\" from=\"340.0\" to=\"190.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"164.0ms\" dur=\"5000.0ms\" attributeName=" +
              "\"y\" from=\"82000.0\" to=\"82000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"87500.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"x\" from=\"190.0\" to=\"190.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"175.0ms\" dur=\"5000.0ms\" attributeName" +
              "=\"y\" from=\"87500.0\" to=\"87500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"108500.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"x\" from=\"190.0\" to=\"190.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"217.0ms\" dur=\"5000.0ms\" attributeName" +
              "=\"y\" from=\"108500.0\" to=\"108500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"114000.0ms\" dur=\"5000.0ms\" attribute" +
              "Name=\"x\" from=\"190.0\" to=\"490.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"228.0ms\" dur=\"5000.0ms\" attributeName=" +
              "\"y\" from=\"114000.0\" to=\"114000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"119500.0ms\" dur=\"5000.0ms\" attribute" +
              "Name=\"x\" from=\"490.0\" to=\"490.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"239.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"119500.0\" to=\"119500.0\" fill=\"freeze\" />\n" +
              "</rect>\n" +
              "<rect id=\"disk2\" x=\"167.5\" y=\"210.0\" width=\"65.0\" height=\"30.0\" fil" +
              "l=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
              "<animate attributeType=\"xml\" begin=\"28500.0ms\" dur=\"5000.0ms\" attribute" +
              "Name=\"x\" from=\"167.5\" to=\"167.5\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"57.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"28500.0\" to=\"28500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"34000.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"x\" from=\"167.5\" to=\"317.5\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"68.0ms\" dur=\"5000.0ms\" attributeName=" +
              "\"y\" from=\"34000.0\" to=\"34000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"39500.0ms\" dur=\"5000.0ms\" attribute" +
              "Name=\"x\" from=\"317.5\" to=\"317.5\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"79.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"39500.0\" to=\"39500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"92500.0ms\" dur=\"5000.0ms\" attribut" +
              "eName=\"x\" from=\"317.5\" to=\"317.5\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"185.0ms\" dur=\"5000.0ms\" attributeName" +
              "=\"y\" from=\"92500.0\" to=\"92500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"98000.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"x\" from=\"317.5\" to=\"467.5\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"196.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"98000.0\" to=\"98000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"103500.0ms\" dur=\"5000.0ms\" attributeN" +
              "ame=\"x\" from=\"467.5\" to=\"467.5\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"207.0ms\" dur=\"5000.0ms\" attributeName" +
              "=\"y\" from=\"103500.0\" to=\"103500.0\" fill=\"freeze\" />\n" +
              "</rect>\n" +
              "<rect id=\"disk3\" x=\"145.0\" y=\"240.0\" width=\"110.0\" height=\"30.0\" fill" +
              "=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
              "<animate attributeType=\"xml\" begin=\"60500.0ms\" dur=\"5000.0ms\" attributeN" +
              "ame=\"x\" from=\"145.0\" to=\"145.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"121.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"y\" from=\"60500.0\" to=\"60500.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"66000.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"x\" from=\"145.0\" to=\"445.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"132.0ms\" dur=\"5000.0ms\" attributeNam" +
              "e=\"y\" from=\"66000.0\" to=\"66000.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"71500.0ms\" dur=\"5000.0ms\" attributeName" +
              "=\"x\" from=\"445.0\" to=\"445.0\" fill=\"freeze\" />\n" +
              "<animate attributeType=\"xml\" begin=\"143.0ms\" dur=\"5000.0ms\" attributeNa" +
              "me=\"y\" from=\"71500.0\" to=\"71500.0\" fill=\"freeze\" />\n" +
              "</rect>\n" +
              "</svg>\n" +
              "\n");
    } catch (IOException | NullPointerException var7) {
      throw new IllegalStateException("File read error.");
    }
  }

  @Test
  public void testSVGViewSizeChange() {
    SimpleAnimatorModel model = new SimpleAnimatorModel(new ArrayList());
    SVGView svgView = new SVGView(model, "SVGData.xml");
    SVGController ctrl = new SVGController(svgView, 1.0D);
    Shape square = new Rectangle("", new CartPt(100.0D, 100.0D),
            new Size(10.0D, 10.0D), this.red);
    Command c = new Command(square, 0.0D, 1000.0D,
            new CartPt(100.0D, 100.0D), new Size(100.0D, 100.0D), this.red);
    model.addShape(square);
    model.addCommand(square, c);
    ctrl.startProgram();

    try {
      assertEquals(Files.readString(Path.of("SVGData.xml")),
              "<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
                      "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                      "\n" +
                      "<rect id=\"\" x=\"100.0\" y=\"100.0\" width=\"10.0\" height=\"10.0\"" +
                      " fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"500000.0ms\" at" +
                      "tributeName=\"width\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"0.0ms\" dur=\"500000.0ms\" attr" +
                      "ibuteName=\"height\" from=\"10.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "</rect>\n" +
                      "</svg>\n" +
                      "\n");
    } catch (IOException | NullPointerException var7) {
      throw new IllegalStateException("File read error.");
    }
  }

  @Test
  public void testReadFileSVG() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("toh-3.txt", new
            AnimatorModelBuilder());
    SVGView svgView = new SVGView(model, "SVGData.xml");
    SVGController ctrl = new SVGController(svgView, 1.0D);
    ctrl.startProgram();

    try {
      assertEquals(Files.readString(Path.of("SVGData.xml")),
              "<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
                      "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                      "\n" +
                      "<rect id=\"disk1\" x=\"190.0\" y=\"180.0\" width=\"20.0\" height=\"30.0\"" +
                      " fill=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"25.0ms\" dur=\"10000" +
                      ".0ms\" attribut" +
                      "e" +
                      "Name=\"x\" from=\"190.0\" to=\"190.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(190.0, 180.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"180.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"36.0ms\" dur=\"10000.0ms\" attribut" +
                      "eName=\"x\" from=\"190.0\" to=\"490.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(190.0, 50.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"47.0ms\" dur=\"10000.0ms\" attribu" +
                      "teName=\"x\" from=\"490.0\" to=\"490.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(490.0, 50.0)ms\" dur=\"10000.0ms" +
                      "\" attributeName=\"y\" from=\"50.0\" to=\"240.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"89.0ms\" dur=\"10000.0ms\" attrib" +
                      "uteName=\"x\" from=\"490.0\" to=\"490.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(490.0, 240.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"240.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"490.0\" to=\"340.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(490.0, 50.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"111.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"340.0\" to=\"340.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(340.0, 50.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"50.0\" to=\"210.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"153.0ms\" dur=\"10000.0ms\" attrib" +
                      "uteName=\"x\" from=\"340.0\" to=\"340.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(340.0, 210.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"210.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"164.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"340.0\" to=\"190.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(340.0, 50.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"175.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"190.0\" to=\"190.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(190.0, 50.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"50.0\" to=\"240.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"217.0ms\" dur=\"10000.0ms\" attri" +
                      "buteName=\"x\" from=\"190.0\" to=\"190.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(190.0, 240.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"240.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"228.0ms\" dur=\"10000.0ms\" attri" +
                      "buteName=\"x\" from=\"190.0\" to=\"490.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(190.0, 50.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"239.0ms\" dur=\"10000.0ms\" att" +
                      "ributeName=\"x\" from=\"490.0\" to=\"490.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(490.0, 50.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"50.0\" to=\"180.0\" fill=\"freeze\" />\n" +
                      "</rect>\n" +
                      "<rect id=\"disk2\" x=\"167.5\" y=\"210.0\" width=\"65.0\" height=\"30." +
                      "0\" fill=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"57.0ms\" dur=\"10000.0ms\" att" +
                      "ributeName=\"x\" from=\"167.5\" to=\"167.5\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(167.5, 210.0)ms\" dur=\"10000." +
                      "0ms\" attributeName=\"y\" from=\"210.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"68.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"167.5\" to=\"317.5\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(167.5, 50.0)ms\" dur=\"10000." +
                      "0ms\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"79.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"317.5\" to=\"317.5\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(317.5, 50.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"50.0\" to=\"240.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"185.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"317.5\" to=\"317.5\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(317.5, 240.0)ms\" dur=\"10000." +
                      "0ms\" attributeName=\"y\" from=\"240.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"196.0ms\" dur=\"10000.0ms\" att" +
                      "ributeName=\"x\" from=\"317.5\" to=\"467.5\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(317.5, 50.0)ms\" dur=\"10000" +
                      ".0ms\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"207.0ms\" dur=\"10000.0ms\" at" +
                      "tributeName=\"x\" from=\"467.5\" to=\"467.5\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(467.5, 50.0)ms\" dur=\"10000." +
                      "0ms\" attributeName=\"y\" from=\"50.0\" to=\"210.0\" fill=\"freeze\" />\n" +
                      "</rect>\n" +
                      "<rect id=\"disk3\" x=\"145.0\" y=\"240.0\" width=\"110.0\" height=\"30" +
                      ".0\" fill=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"121.0ms\" dur=\"10000.0ms\" att" +
                      "ributeName=\"x\" from=\"145.0\" to=\"145.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(145.0, 240.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"240.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"132.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"145.0\" to=\"445.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(145.0, 50.0)ms\" dur=\"10000.0m" +
                      "s\" attributeName=\"y\" from=\"50.0\" to=\"50.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"143.0ms\" dur=\"10000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"445.0\" to=\"445.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(445.0, 50.0)ms\" dur=\"10000.0" +
                      "ms\" attributeName=\"y\" from=\"50.0\" to=\"240.0\" fill=\"freeze\" />\n" +
                      "</rect>\n" +
                      "</svg>\n" +
                      "\n");
    } catch (IOException | NullPointerException var7) {
      throw new IllegalStateException("File read error.");
    }
  }

  @Test
  public void testReadFileSVGHos() throws FileNotFoundException {
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModel model = reader.readFile("hos-y-juke.txt", new
            AnimatorModelBuilder());
    SVGView svgView = new SVGView(model, "SVGData.xml");
    SVGController ctrl = new SVGController(svgView, 1.0D);
    ctrl.startProgram();

    try {
      assertEquals(Files.readString(Path.of("SVGData.xml")),
              "<svg width=\"700\" height=\"500\" version=\"1.1\"\n" +
                      "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                      "\n" +
                      "<ellipse id=\"QB\" cx=\"250.0\" cy=\"385.0\" ry=\"10.0\" rx=\"10.0\" " +
                      "fill=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"90000.0ms\" attribu" +
                      "teName=\"x\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(250.0, 385.0)ms\" dur=\"90000.0m" +
                      "s\" attributeName=\"y\" from=\"385.0\" to=\"400.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"Ball\" cx=\"250.0\" cy=\"385.0\" ry=\"3.0\" rx=\"7.0\" fi" +
                      "ll=\"rgb(210,30,105)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"90000.0ms\" attrib" +
                      "uteName=\"x\" from=\"250.0\" to=\"250.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(250.0, 385.0)ms\" dur=\"90000.0m" +
                      "s\" attributeName=\"y\" from=\"385.0\" to=\"400.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"200000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"250.0\" to=\"190.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(250.0, 385.0)ms\" dur=\"200000.0" +
                      "ms\" attributeName=\"y\" from=\"385.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"C\" cx=\"250.0\" cy=\"375.0\" ry=\"10.0\" rx=\"10.0\" fil" +
                      "l=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"RG\" cx=\"265.0\" cy=\"377.0\" ry=\"10.0\" rx=\"10.0\" f" +
                      "ill=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"RT\" cx=\"280.0\" cy=\"381.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"LG\" cx=\"235.0\" cy=\"377.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"LT\" cx=\"220.0\" cy=\"381.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(0,0,0)\" visibility=\"visible\" >\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"wrX\" cx=\"100.0\" cy=\"375.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(0,200,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"90000.0ms\" attri" +
                      "buteName=\"x\" from=\"100.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(100.0, 375.0)ms\" dur=\"90000.0m" +
                      "s\" attributeName=\"y\" from=\"375.0\" to=\"300.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"wrY\" cx=\"150.0\" cy=\"375.0\" ry=\"10.0\" rx=\"10.0\" " +
                      "fill=\"rgb(200,0,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"290000.0ms\" att" +
                      "ributeName=\"x\" from=\"150.0\" to=\"190.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(150.0, 275.0)ms\" dur=\"290000" +
                      ".0ms\" attributeName=\"y\" from=\"275.0\" to=\"100.0\" fill=\"fr" +
                      "eeze\" />\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"HB\" cx=\"200.0\" cy=\"385.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(0,150,150)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"90000.0ms\" attri" +
                      "buteName=\"x\" from=\"200.0\" to=\"240.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(200.0, 385.0)ms\" dur=\"90000." +
                      "0ms\" attributeName=\"y\" from=\"385.0\" to=\"300.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"200000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"240.0\" to=\"175.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(240.0, 300.0)ms\" dur=\"200000.0" +
                      "ms\" attributeName=\"y\" from=\"300.0\" to=\"300.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"wrZ\" cx=\"350.0\" cy=\"390.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(0,150,150)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"290000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"350.0\" to=\"360.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(350.0, 390.0)ms\" dur=\"290000." +
                      "ms\" attributeName=\"y\" from=\"390.0\" to=\"100.0\" fill=\"freeze\" />\n" +
                      "</ellipse>\n" +
                      "<ellipse id=\"TE\" cx=\"360.0\" cy=\"380.0\" ry=\"10.0\" rx=\"10.0\" fi" +
                      "ll=\"rgb(150,150,0)\" visibility=\"visible\" >\n" +
                      "<animate attributeType=\"xml\" begin=\"10.0ms\" dur=\"290000.0ms\" attr" +
                      "ibuteName=\"x\" from=\"360.0\" to=\"360.0\" fill=\"freeze\" />\n" +
                      "<animate attributeType=\"xml\" begin=\"(360.0, 380.0)ms\" dur=\"290000." +
                      "0ms\" attributeName=\"y\" from=\"380.0\" to=\"300.0\" fill=\"freeze\" />" +
                      "\n" +
                      "</ellipse>\n" +
                      "</svg>\n" +
                      "\n");
    } catch (IOException | NullPointerException var7) {
      throw new IllegalStateException("File read error.");
    }

  }

}
