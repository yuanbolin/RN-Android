package com.ruowei.baseandroid.utils;

import android.content.Context;
import android.os.Environment;


import com.ruowei.baseandroid.MyApplication;

import java.io.File;

/**
 * 如果想要存储所有的json数据，可以在下面写在文件里面
 * 判断sd卡是否可用
 * 获取sd卡路径
 */
public class FileUtils {
    private Context context;

    public FileUtils() {
        context = MyApplication.getMyApplicationContext();
    }

    /***
     * 获取文件类型
     *
     * @param path 文件路径
     * @return 文件的格式
     */
    public static String getFileType(String path) {
        String str = "";

        if (EmptyUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }

    //判断sd卡是否可用
    public boolean isSDCard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    //mnt/sdcard/Android/data/包名/files
    public String getSDFile() {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }


    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    public void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

	/*
    //获取保存在本地的json文件
	public String readJson(String fileName){
		String result = null;
		File file = new File(getSDFile()+"/"+fileName);
		if (!file.exists()) {
			return null;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader is = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(is);
			String str;
			StringBuffer sbf = new StringBuffer();
			while((str=br.readLine())!=null){
				sbf.append(str);
			}
			br.close();
			is.close();
			fis.close();
			result = sbf.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	*//**
     * 保存json的工具类
     * @param json   需要保存的json
     * @param fileName  保存的文件名
     *//*
	public void saveJson(String json,String fileName){
		if (json==null) {
			return;
		}
		if (fileName==null) {
			return;
		}
		//保存
		if (!isSDCard()) {
			return;
		}
		//保存文件的绝对路径
		String filePath = getSDFile()+"/"+fileName;
		try {
			OutputStream os = new FileOutputStream(filePath);
			os.write(json.getBytes());
			os.flush();
			os.close();
			Log.i("TAG", "保存json成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}*/
}
