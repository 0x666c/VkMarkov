import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Yes {

	public static void main(String[] args) throws Exception {
		ArrayList<String> segments = new ArrayList<>();
		Files.walk(Paths.get("C:\\Users\\New\\Desktop\\chat\\")).forEach(path -> {
			if (path.toFile().isDirectory())
				return;
			try {
				FileReader r = new FileReader(path.toFile());
				segments.add(new BufferedReader(r).lines().map(line -> line.replaceAll("\n", "")).map(line -> line.replace("}", "}\n")).map(line -> line.replaceAll("\\{|\\}", "")).filter(line -> {
					char[] arr = line.toCharArray();
					for (int i = 0; i < arr.length; i++) {
						if (arr[i] < 65 || arr[i] > 255)
							return true;
					}
					return false;
				}).filter(line -> !line.startsWith("chat_") && !line.startsWith("http") && !line.startsWith("id")).map(line -> line.trim()+"\n").reduce("", String::concat));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		
		for (String seg : segments) {
			char[] ch = seg.toCharArray();
			String filtered = "";
			for (int i = 0; i < ch.length; i++) {
				if(Character.isAlphabetic(ch[i]) || ch[i] == '\n' || ch[i] == ' ')
					filtered += ch[i];
			}
			filtered = filtered.replaceAll(" +", " ").toLowerCase();
			
			String[] tokens = filtered.split("\n");
			StringBuilder resultBuilder = new StringBuilder();
			Set<String> alreadyPresent = new HashSet<String>();

			boolean first = true;
			for(String token : tokens) {

			    if(!alreadyPresent.contains(token) && (token.contains(" ")) && !token.endsWith(" ") && !token.startsWith(" ") && !token.contains("id")) {
			        if(first) first = false;
			        else resultBuilder.append("\n");

			        if(!alreadyPresent.contains(token))
			            resultBuilder.append(token);
			    }

			    alreadyPresent.add(token);
			}
			filtered = resultBuilder.toString();
			
			FileWriter w = new FileWriter(new File("C:\\Users\\New\\Desktop\\filtered.txt"), true);
			w.write(filtered);
			w.write("\n");
			w.flush();
			w.close();
		}
		
		//System.out.println(filtered);
	}

}