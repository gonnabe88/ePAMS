package epams.domain.com.weddingFuneral;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/***
 * @author 210058
 * @implNote 경조사 controller
 * @since 2024-09-18
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/common")
public class WeddingFuneralController {
    
    @GetMapping("/weddingFuneral")
    public String weddingFuneral(final Model model) {

        String url="http://gwe.kdb.co.kr:20080/jsp/kdb/bbs/kdb_bbs_holiday.jsp?empcode=K210058";

        //경조사 목록
        ArrayList<String> eventList=listContent(url);
        model.addAttribute("eventList", eventList);

        return "common/weddingFuneral";
    }

    public static ArrayList<String> listContent(String urlString){
        //url 안에A  내용 찾기
        StringBuilder content = new StringBuilder();
        ArrayList<String> subjects = new ArrayList<>();

        try{
            URL url=new URL(urlString);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                while((inputLine = in.readLine())!=null){
                    content.append(inputLine);
                }
                in.close();
            }else{
                System.out.println("GET 방식실패!");
                return null;
            }

            //1단계 : <bblist>안 내용 추출
            Pattern listPattern = Pattern.compile("<bbslist>(.*?)</bbslist>",Pattern.DOTALL);
            Matcher listMatcher = listPattern.matcher(content.toString()); 

            if(listMatcher.find()){
                String listContent = listMatcher.group(1);

                //2단계 : <mtrl> 안 내용 추출
                Pattern mtrlPattern = Pattern.compile("<mtrl>.*?<subject>(.*?)</subject>.*?<mtrl>",Pattern.DOTALL);
                Matcher mtrlMatcher = mtrlPattern.matcher(listContent);

                while(mtrlMatcher.find()){
                    subjects.add(mtrlMatcher.group(1));
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return subjects;
    }
    
}
