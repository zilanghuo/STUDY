package com.mouse.study.test.growing;

import com.mouse.study.utils.HttpsClientUtils;
import com.mouse.study.utils.ZipUtils;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lwf on 2017/8/18.
 * use to do:
 * 项目ID  AI  b93a6913b8d3edb9
 * 项目私钥  BesparUPrayegEd4faReTAVUjuqAka7u
 * 项目公钥（X-Client-Id）: b8t77weRaMeDruMAtrAhUPU8AjaphaVe
 * 项目UID ：1P6VAXPd
 * <p>
 * 身份验签：  b8t77weRaMeDruMAtrAhUPU8AjaphaVe  42702cac79e509375c3c8c98cd70551e7945cbdc5aff83044648d048d081b4a9
 * <p>
 * <p>
 * <p>
 * 42702cac79e509375c3c8c98cd70551e7945cbdc5aff83044648d048d081b4a9
 * 59ac15d096d025eb815e63c61d1246e7ebce3b0e5ae34de5fad7c0b52b3ac264
 * <p>
 */
public class Test {

    public static String url_token = "https://www.growingio.com/auth/token";

    public static String url_get_data_remote = "https://www.growingio.com/insights/b93a6913b8d3edb9/2017082108.json";

    public static String url_get_data = "https://www.growingio.com/insights";

    public static void main(String[] args) throws Exception {
        unGzipFile();
    }

    public static void unGzipFile() throws Exception{
        String sourcedir = "F:\\part-00000.gz";
        ZipUtils.decompressNewFile(sourcedir);

    }


    /**
     * 读取文件
     *
     * @throws Exception
     */
    public static void readForGZ() throws Exception {
        String fileName = "F:\\part-00000\\part-00000";
        String FileContent = "";
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            FileContent += line;
            FileContent += "\r\n"; // 补上换行符
            System.out.println(line);
        }
    }

    /**
     * 读取远程数据接口
     */
    public static void readDate() {
        String fileName = "F:\\part-00000\\part-00000";
        String FileContent = ""; // 文件很长的话建议使用StringBuffer
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                FileContent += line;
                FileContent += "\r\n"; // 补上换行符
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据接口
     * "downlinks":[
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_page_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T035629Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=e19223db09e87cb1b260fd3924428de59d92ffc3b0e451618ee683e33ccf4d91",
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_visit_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T035629Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=a7852e3f9bc1169aa0d435594e0eee42483e8481b428b291695576a52b283d68",
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_action_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T035629Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=96375167b2a78e50e131ed752046c73b1469ea591a6d27ed0457342948bf192a",
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_action_tag_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T035629Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=0af590db0ed20083922995b4f22be40a1c0bd7f94b43d8fa708afa3fcbd1b724"]}
     */
    public static void getData() {

        String ai = "b93a6913b8d3edb9";
        String date = "2017082108";
        String url_data = url_get_data + "/" + ai + "/" + date + ".json";
        Map<String, String> valueMap = new HashMap();

        valueMap.put("expire", "10");
        Map<String, String> headMap = new HashMap();
        headMap.put("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");
        headMap.put("Authorization", "WNKGA8tpdS7KD2CxwboaBrGxKzOSCd15YkWMcZW84l8qhlYHV2qbHaKsLjcqiSXL");

        HttpsClientUtils clientUtils = new HttpsClientUtils();
        try {
            clientUtils.get(url_data, valueMap, headMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据
     */
    public static void getDateForHour() {
        Map<String, String> headMap = new HashMap();
        headMap.put("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");
        headMap.put("Authorization", "yvHDiQlWQOirrqG7MQnCJQcJfTYJYURmVHtDauCRs5yFDwevmwl6TMAtg6i8EYKx");
        Map<String, String> valueMap = new HashMap();
        //  valueMap.put("ai", "b93a6913b8d3edb9");
        //  valueMap.put("date", "2017082108");
        valueMap.put("expire", "1");
        try {
            HttpsClientUtils.get(url_get_data, valueMap, headMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * growing 接口认证
     * yvHDiQlWQOirrqG7MQnCJQcJfTYJYURmVHtDauCRs5yFDwevmwl6TMAtg6i8EYKx
     */
    private static void testToken() {
        Map<String, String> valueMap = new HashMap();
        Map<String, String> headMap = new HashMap();
        headMap.put("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");

        valueMap.put("ai", "b93a6913b8d3edb9");
        valueMap.put("project", "1P6VAXPd");
        valueMap.put("tm", "1503368721219");
        valueMap.put("auth", "59ac15d096d025eb815e63c61d1246e7ebce3b0e5ae34de5fad7c0b52b3ac264");
        try {
            HttpsClientUtils.post(url_token, valueMap, headMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * {"status":"success","code":"5axRCtECNqNZ6c9sNuxpWnoihYr8VEr0oxYA0Q5ga7cSM2QYy3jkuoT4pR5Ryako"}Resp Code:200
         */


    }


    public static String authToken(String secret, String project, String ai, Long tm) throws Exception {
        String message = "POST\n/auth/token\nproject=" + project + "&ai=" + ai + "&tm=" + tm;
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signature = hmac.doFinal(message.getBytes("UTF-8"));
        return Hex.encodeHexString(signature);
    }
}
