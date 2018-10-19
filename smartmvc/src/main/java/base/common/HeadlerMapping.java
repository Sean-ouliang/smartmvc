package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * ӳ�䴦������
 *   �����ṩ����·���봦�����Ķ�Ӧ��ϵ��
 * @author tarena
 *
 */
public class HeadlerMapping {
	/*
	 * headlerMap���ڴ������·���봦�����Ķ�Ӧ��ϵ
	 * ע��
	 *   key������·��
	 *   value�Ǵ����������������������һ����װ��
	 */
	private Map<String,Headler> headlerMap = new HashMap<String,Headler>();
	
	/**
	 * ��������·�������ش�����ʵ�������������������
	 * ��װ(��Headler����)��
	 * @param path
	 * @return
	 */
	public Headler getHeadler(String path){
		return headlerMap.get(path);
	}

	/**
	 * ���ڽ�������·���봦�����Ķ�Ӧ��ϵ��
	 * �÷�������list����(�����д�ŵ��Ǵ�����ʵ����
	 * ����HelloController),����ÿ��������ʵ��������
	 * java������ƶ�ȡ����ǰ���@RequestMappingע����
	 * ��·����Ϣ��Ȼ����·����Ϣ��Ϊkey���Դ�����ʵ����
	 * ����������������Ϊvalue���������Ӧ��ϵ��ӵ���
	 * headlerMap���档
	 * 
	 * @param beans
	 */
	public void process(List beans) {
		for(Object bean : beans){
			//���Class����
			Class clazz = bean.getClass();
			//������з���
			Method[] methods = clazz.getDeclaredMethods();
			for(Method mh : methods){
				//��÷���ǰ���@RequestMappingע��
				RequestMapping rm = mh.getDeclaredAnnotation(RequestMapping.class);
				//���@RequestMapping������ֵ��������·����
				String path = rm.value();
				/*
				 * ������·����Ϊkey���Դ�����ʵ����
				 * ������������Ϊvalue����������·��
				 * �봦�����Ķ�Ӧ��ϵ
				 */
				headlerMap.put(path, new Headler(mh,bean));
			}
		}
		System.out.println("headlerMap:"+headlerMap);
	}
	
}
