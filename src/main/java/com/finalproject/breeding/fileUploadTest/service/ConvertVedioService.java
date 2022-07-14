package com.finalproject.breeding.fileUploadTest.service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ConvertVedioService {

    FFmpeg ffmpeg;
    FFprobe ffprobe;
    @PostConstruct
    public void init() {
        try {
            // 1. 썸네일을 추출하고자 하는 영상 파일의 절대 경로 설정
            //String inputPath = "Users/hojunna/Download/my/";
            //String outputPath = "/usr/local/video/thumbnail/";

            // 2. FFmpeg 파일 경로 설정
            String ffmpegBasePath = "/opt/homebrew/bin/";
            ffmpeg = new FFmpeg(ffmpegBasePath+"ffmpeg");		// ffmpeg.exe 파일 경로
            ffprobe = new FFprobe("/opt/homebrew/bin/ffprobe");	// ffprobe.exe 파일 경로
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public File videoEncode(File file) throws IOException {
        // FFmpegProbeResult probeResult = ffprobe.probe(String.valueOf(file));

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput("/Users/hojunna/Desktop/" + file)     					// 썸네일 이미지 추출에 사용할 영상 파일의 절대 경로
                .overrideOutputFiles(true)					// output 파일을 덮어쓸 것인지 여부(false일 경우, output path에 해당 파일이 존재할 경우 예외 발생 - File 'C:/Users/Desktop/test.png' already exists. Exiting.)
                .addOutput("/Users/hojunna/Desktop/test.mp4") 		// 저장 절대 경로(확장자 미 지정 시 예외 발생 - [NULL @ 000002cc1f9fa500] Unable to find a suitable output format for 'C:/Users/Desktop/test')
                .setFormat("mp4")
                .setVideoResolution(426,240)
                .setDuration(15, TimeUnit.SECONDS)
                //.setVideoFilter("select='gte(n\\,10)',scale=200:-1")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);
        executor.createJob(builder).run();
        return file;
    }

}
