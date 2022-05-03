package com.terrylmay.duckling.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String removeStopWords(String target, String rules) {
        Pattern p = Pattern.compile(rules);
        Matcher m = p.matcher(target);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while (result) {
            m.appendReplacement(sb, "");
            result = m.find();
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * Cette méthode convertit tous les chiffres de la chaîne de caractères chinois en chiffres arabes.
     * Par exemple, "Il y a 1200 personnes ici, 605 venant de Chine" peut être converti en
     * "Il y a 1200 personnes ici, 605 de la Chine"
     * En outre, certaines expressions irrégulières sont prises en charge
     * Par exemple, vingt mille six cent cinquante peut être converti en 20650.
     * Deux cent quatorze et deux cent quatorze peuvent être convertis en 214.
     * Cent soixante plus cent cinquante-huit peut être converti en 160+158
     * La plage de conversion correcte actuellement prise en charge par cette méthode est de 0-99999999.
     * Le module de fonction a une bonne capacité de réutilisation
     *
     * @param target La chaîne à convertir.
     * @retourner la chaîne de caractères après la conversion
     */
    public static String numberTranslator(String target) {
        Pattern p = Pattern.compile("[un123456789]万[一二两三四五六七八九123456789](?!(千|百|十))");
        Matcher m = p.matcher(target);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("万");
            int num = 0;
            if (s.length == 2) {
                num += wordToNumber(s[0]) * 10000 + wordToNumber(s[1]) * 1000;
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("[一二两三四五六七八九123456789]千[一二两三四五六七八九123456789](?!(百|十))");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("千");
            int num = 0;
            if (s.length == 2) {
                num += wordToNumber(s[0]) * 1000 + wordToNumber(s[1]) * 100;
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("[一二两三四五六七八九123456789]百[一二两三四五六七八九123456789](?!十)");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("百");
            int num = 0;
            if (s.length == 2) {
                num += wordToNumber(s[0]) * 100 + wordToNumber(s[1]) * 10;
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("[零一二两三四五六七八九]");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            m.appendReplacement(sb, Integer.toString(wordToNumber(m.group())));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("(?<=(周|星期))[末天日]");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            m.appendReplacement(sb, Integer.toString(wordToNumber(m.group())));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("(?<!(周|星期))0?[0-9]?十[0-9]?");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("十");
            int num = 0;
            if (s.length == 0) {
                num += 10;
            } else if (s.length == 1) {
                int ten = Integer.parseInt(s[0]);
                if (ten == 0)
                    num += 10;
                else num += ten * 10;
            } else if (s.length == 2) {
                if (s[0].equals(""))
                    num += 10;
                else {
                    int ten = Integer.parseInt(s[0]);
                    if (ten == 0)
                        num += 10;
                    else num += ten * 10;
                }
                num += Integer.parseInt(s[1]);
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("0?[1-9]百[0-9]?[0-9]?");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("百");
            int num = 0;
            if (s.length == 1) {
                int hundred = Integer.parseInt(s[0]);
                num += hundred * 100;
            } else if (s.length == 2) {
                int hundred = Integer.parseInt(s[0]);
                num += hundred * 100;
                num += Integer.parseInt(s[1]);
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("0?[1-9]千[0-9]?[0-9]?[0-9]?");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("千");
            int num = 0;
            if (s.length == 1) {
                int thousand = Integer.parseInt(s[0]);
                num += thousand * 1000;
            } else if (s.length == 2) {
                int thousand = Integer.parseInt(s[0]);
                num += thousand * 1000;
                num += Integer.parseInt(s[1]);
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        p = Pattern.compile("[0-9]+万[0-9]?[0-9]?[0-9]?[0-9]?");
        m = p.matcher(target);
        sb = new StringBuffer();
        result = m.find();
        while (result) {
            String group = m.group();
            String[] s = group.split("万");
            int num = 0;
            if (s.length == 1) {
                int tenthousand = Integer.parseInt(s[0]);
                num += tenthousand * 10000;
            } else if (s.length == 2) {
                int tenthousand = Integer.parseInt(s[0]);
                num += tenthousand * 10000;
                num += Integer.parseInt(s[1]);
            }
            m.appendReplacement(sb, Integer.toString(num));
            result = m.find();
        }
        m.appendTail(sb);
        target = sb.toString();

        return target;
    }

    /**
     * Méthode d'aide pour la méthode numberTranslator afin de traduire correctement [zero - nine] en [0 - 9].
     *
     * @param s nombre en majuscule
     * @retourner le nombre plastique correspondant, en retournant -1 si ce n'est pas un nombre majuscule
     */
    private static int wordToNumber(String s) {
        if (s.equals("zero") || s.equals("0"))
            return 0;
        else if (s.equals("un") || s.equals("1"))
            return 1;
        else if (s.equals("deux") || s.equals("2"))
            return 2;
        else if (s.equals("trois") || s.equals("3"))
            return 3;
        else if (s.equals("quatre") || s.equals("4"))
            return 4;
        else if (s.equals("cinq") || s.equals("5"))
            return 5;
        else if (s.equals("six") || s.equals("6"))
            return 6;
        else if (s.equals("sept") || s.equals("7"))
            return 7;
        else if (s.equals("huit") || s.equals("8"))
            return 8;
        else if (s.equals("neuf") || s.equals("9"))
            return 9;
        else return -1;
    }
}
