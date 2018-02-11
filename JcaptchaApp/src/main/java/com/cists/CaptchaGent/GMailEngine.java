package com.cists.CaptchaGent;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.Serializable;
import java.util.Locale;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 仿照JCaptcha2.0编写GMail验证码样式的图片引擎.
 * 
 * @author calvin
 */
public class GMailEngine extends ListImageCaptchaEngine {
    @Override
    protected void buildInitialFactories() {
        int minWordLength = 4;
        int maxWordLength = 4;
        int fontSize = 60;
        int imageWidth = 250;
        int imageHeight = 100;

        // word generator
        WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

        // word2image components
        TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength, new RandomListColorGenerator(new Color[] {
                new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }), new TextDecorator[] {});
        BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
        FontGenerator font = new RandomFontGenerator(fontSize, fontSize, new Font[] { new Font("nyala", Font.BOLD, fontSize),
                new Font("Bell MT", Font.PLAIN, fontSize), new Font("Credit valley", Font.BOLD, fontSize) });

        ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
        ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
        ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});

        WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef, postDef);

        GimpyFactory factry = new GimpyFactoryCustom(dictionnaryWords, word2image);

        addFactory(factry);
    }

    class GimpyFactoryCustom extends GimpyFactory {

        public GimpyFactoryCustom(WordGenerator generator, WordToImage word2image) {
            super(generator, word2image);
        }

        public ImageCaptcha getImageCaptcha(Locale locale) {
            Integer wordLength = getRandomLength();

//            String word = getWordGenerator().getWord(wordLength, locale);
            String word = CaptchaImageCreateController.CODETOCREATE.get();
            if(word==null){
                word = getWordGenerator().getWord(wordLength, locale);
            }
            BufferedImage image = null;
            try {
                image = getWordToImage().getImage(word);
            } catch (Throwable e) {
                throw new CaptchaException(e);
            }
            ImageCaptcha captcha = new Gimpy(CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY), image, word);

            return captcha;
        }

    }

    class Gimpy extends ImageCaptcha implements Serializable {
        private String response;

        Gimpy(String question, BufferedImage challenge, String response) {
            super(question, challenge);
            this.response = response;
        }

        public final Boolean validateResponse(Object response) {
            return (null != response) && ((response instanceof String)) ? validateResponse((String) response) : Boolean.FALSE;
        }

        private final Boolean validateResponse(String response) {
            return Boolean.valueOf(response.equals(this.response));
        }
    }
}