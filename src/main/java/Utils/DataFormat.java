package Utils;

import java.text.NumberFormat;

public class DataFormat {
    public DataFormat() {

    }
   //数据格式化
    public static String dataFormat(Object data) {
        String temp = NumberFormat.getInstance().format(data);
        return  temp;
    }

}
