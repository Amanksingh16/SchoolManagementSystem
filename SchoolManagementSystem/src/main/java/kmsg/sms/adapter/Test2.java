package kmsg.sms.adapter;

import java.io.*;
import java.util.*;

public class Test2{

	static int maxSubsetSum(int[] arr) 
	{
		List<String> list = new ArrayList<>();
		for(int i = 0;i < arr.length; i++)
		{
			int j = i + 2;
			String num = "";
			num = num + String.valueOf(arr[i]);
			while(j < arr.length)
			{
				num = num + String.valueOf(arr[j]);
				
				list.add(num);	
				
				if((j + 2) > arr.length)
					break;
				
				j = j + 2;
			}
		}
		for(int i = 0; i < list.size(); i++)
    	{
    		System.out.println(list.get(i));
    	}
		return 0;
    }


    public static void main(String[] args) throws IOException {
    	
    	Scanner sc = new Scanner(System.in);
    	
    	String [] sarr = sc.next().split(" ");
    	int arr[] = new int[sarr.length];
    	for(int i = 0; i < sarr.length; i++)
    	{
    		arr[i] = Integer.parseInt(sarr[i]);
    	}
    	
    	maxSubsetSum(arr);
    	sc.close();
    }
}
