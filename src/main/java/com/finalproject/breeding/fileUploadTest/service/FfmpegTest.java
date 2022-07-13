package com.finalproject.breeding.fileUploadTest.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class FfmpegTest {
    @Value("${ffmpeg.path}")
    private String ffmpegPath;
    @Value("${ffprove.path}")
    private String ffprobePath;
    FFmpegBuilder builder = new FFmpegBuilder()

            .setInput("input.mp4")     // Filename, or a FFmpegProbeResult
            .overrideOutputFiles(true) // Override the output if it exists

            .addOutput("output.mp4")   // Filename for the destination
            .setFormat("mp4")        // Format is inferred from filename, or can be set
            .setTargetSize(250_000)  // Aim for a 250KB file

            .disableSubtitle()       // No subtiles

            .setAudioChannels(1)         // Mono audio
            .setAudioCodec("aac")        // using the aac codec
            .setAudioSampleRate(48_000)  // at 48KHz
            .setAudioBitRate(32768)      // at 32 kbit/s

            .setVideoCodec("libx264")     // Video using x264
            .setVideoFrameRate(24, 1)     // at 24 frames per second
            .setVideoResolution(640, 480) // at 640x480 resolution

            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
            .done();

/*    FFmpegExecutor executor = new FFmpegExecutor(ffmpegPath, ffprobePath);

// Run a one-pass encode
executor.createJob(builder).run();

// Or run a two-pass encode (which is better quality at the cost of being slower)
executor.createTwoPassJob(builder).run();*/
}
