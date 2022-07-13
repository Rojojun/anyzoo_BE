package com.finalproject.breeding.fileUploadTest.service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

@Service
public class ThumbnailService {

    public HashMap<String, Object> exportThumbnail(File file) throws Exception {

        // 1. 썸네일을 추출하고자 하는 영상 파일의 절대 경로 설정
        //String inputPath = "Users/hojunna/Download/my/";
        //String outputPath = "/usr/local/video/thumbnail/";

        // 2. FFmpeg 파일 경로 설정
        String ffmpegBasePath = "/opt/homebrew/bin/";
        FFmpeg ffmpeg = new FFmpeg(ffmpegBasePath+"ffmpeg");		// ffmpeg.exe 파일 경로
        FFprobe ffprobe = new FFprobe(ffmpegBasePath+"ffprobe");	// ffprobe.exe 파일 경로
        // 3. FFmpegBuilder를 통해 FFmpeg 명령어를 만들 수 있음
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput("/Users/hojunna/Desktop/" + file)     					// 썸네일 이미지 추출에 사용할 영상 파일의 절대 경로
                //.overrideOutputFiles(true)					// output 파일을 덮어쓸 것인지 여부(false일 경우, output path에 해당 파일이 존재할 경우 예외 발생 - File 'C:/Users/Desktop/test.png' already exists. Exiting.)
                //.addExtraArgs("-ss", "00:00:01") 			// 영상에서 추출하고자 하는 시간 - 00:00:01은 1초를 의미
                .addOutput("/Users/hojunna/Desktop/test.jpg") 		// 저장 절대 경로(확장자 미 지정 시 예외 발생 - [NULL @ 000002cc1f9fa500] Unable to find a suitable output format for 'C:/Users/Desktop/test')
                .setFrames(1)
                .setVideoFilter("select='gte(n\\,10)',scale=200:-1")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);		// FFmpeg 명령어 실행을 위한 FFmpegExecutor 객체 생성
        executor.createJob(builder).run();									// one-pass encode
 		executor.createTwoPassJob(builder).run();							// two-pass encode*/

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        return resultMap;
    }
}