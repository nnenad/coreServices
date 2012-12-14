/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.lib.regex;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Vuk
 */
public class PatternMatcher {
    public static boolean matchOnce(String regex, String source){
        Pattern pattern =
            Pattern.compile(regex);
            Matcher matcher =
            pattern.matcher(source);

            boolean found = false;
            if (matcher.find()) {
                found = true;
            }
            return found;
    }
    public static ArrayList<MatcherResultEnt> matchAll(String regex, String source){
        ArrayList<MatcherResultEnt> result = new ArrayList<MatcherResultEnt>();
        Pattern pattern =
            Pattern.compile(regex);
            Matcher matcher =
            pattern.matcher(source);

            while (matcher.find()) {
                MatcherResultEnt resultEnt = new MatcherResultEnt();
                resultEnt.setEnd(matcher.end());
                resultEnt.setStart(matcher.start());
                resultEnt.setGroup(matcher.group());
                result.add(resultEnt);
            }
            return result;
    }
}
