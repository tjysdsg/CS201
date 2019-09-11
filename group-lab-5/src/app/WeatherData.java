package app;

import java.lang.ref.Reference;

import app.In;
import app.Out;
import app.Statistics;

public class WeatherData {
	private static String getWordBefore(String html, int i) {
		String word = "";
		while (i >= 0 && !Character.isSpaceChar(html.charAt(i))) {
			word = html.charAt(i--) + word;
		}
		return word;
	}

	private static void findMonthData(String url) {
		int indexCity = url.indexOf("http://www.tianqihoubao.com/lishi/")
				+ "http://www.tianqihoubao.com/lishi/".length();
		int indexCity1 = url.indexOf("/month", indexCity);
		String cityName = url.substring(indexCity, indexCity1);
		System.err.println("Loading for city: " + cityName);

		In page = new In(url);
		String html = page.readAll();
		if (html.length() == 0) {
			return;
		}

		Out o = new Out("downloaded_file.html");
		o.print(html);

		System.err.println("Finished loading");
		String pattern = "<a href='/lishi/" + cityName + "/";
		int lastIndex = 0;
		while (true) {
			int i1 = html.indexOf(pattern, lastIndex);
			if (i1 == -1) {
				// all data has been crawled
				break;
			}

			// extract date
			int i2 = html.indexOf(".html", i1);
			String date = html.substring(i1 + pattern.length(), i2);
			if (date.contains("/")) {
				break;
			}

			// extract temperature data
			String[] temperature = new String[] { "", "" };
			int i3, i4;
			// extract temperature data
			do {
				i3 = html.indexOf("℃", i2);
				if (i3 == -1) {
					break;
				}
				String high = getWordBefore(html, i3 - 1);
				if (high.length() > 0) {
					temperature[0] = high;
				}
			} while (false);

			do {
				i4 = html.indexOf("℃", i3 + 1);
				if (i4 == -1) {
					break;
				}
				String low = getWordBefore(html, i4 - 1);
				if (low.length() > 0) {
					temperature[1] = low;
				}
			} while (false);
			System.out.println(date + "," + temperature[0] + "," + temperature[1]);

			lastIndex = i4;
		}
	}

	private static void downloadWeatherData(String url) {
		if (url.endsWith(".html")) {
			findMonthData(url);
		} else {
			for (int i = 1; i < 13; ++i) {
				findMonthData(url + String.format("%02d", i) + ".html");
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 0) {
			downloadWeatherData(args[0]);
		} else {
			System.out.println("Need command line input for url");
			downloadWeatherData("http://www.tianqihoubao.com/lishi/shanghai/month/201901.html");
		}
	}
}