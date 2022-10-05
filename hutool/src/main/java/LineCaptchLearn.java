import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Console;


/**
 * author:liuch
 * date：2022-10-02
 * desc: 生成二维码图片
 */
public class LineCaptchLearn {

    public static void main(String[] args) {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("F:\\日常文件\\临时目录\\数据\\line1.png");
        //输出code
        Console.log(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");

        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("F:\\日常文件\\临时目录\\数据\\line2.png");
        //新的验证码
        Console.log(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");


        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        //CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("F:\\日常文件\\临时目录\\数据\\line3.png");
        //验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");




        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha2 = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        //ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha2.write("F:\\日常文件\\临时目录\\数据\\line4.png");
        //验证图形验证码的有效性，返回boolean值
        captcha2.verify("1234");



        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha2 = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha2.setGenerator(randomGenerator);
        // 重新生成code
        lineCaptcha2.createCode();
        ShearCaptcha captcha3 = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        // 自定义验证码内容为四则运算方式
        captcha3.setGenerator(new MathGenerator());
        // 重新生成code
        captcha3.createCode();
    }
}
