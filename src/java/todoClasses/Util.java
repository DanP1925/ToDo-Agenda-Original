
package todoClasses;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
    
    public boolean isCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies !=null){
            for(int i = 0;i<cookies.length;i++){
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(int i = 0;i<cookies.length;i++){
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())){
                    String cookieValue;
                    cookieValue = cookie.getValue();
                    return cookieValue;
                }
            }
        }
        return "";
    }
    
    public void setCookie(HttpServletResponse response, String cookieName, String cookieValue,int maxage){
        Cookie c = new Cookie(cookieName,cookieValue);
        c.setMaxAge(maxage);
        response.addCookie(c);
    }
    
    public boolean isGoodFormat(String value){
        return value!=null && (!value.equals(""));
    }
}
