private static String getStringOrDefault(List<Object> list,int index,String defaultValue){
	if (index < list.size()){
		var value = list.get(index);
		if (value instanceof String _str){
			return _str;
		} else {
			return defaultValue;
		}
	} else {
		return defaultValue;
	}
}