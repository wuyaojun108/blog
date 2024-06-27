package org.wyj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class UploadController {
    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Value("${user_file.basedir}")
    private String userFileBaseDir;

    @PostMapping("/upload")
    public String upload(HttpServletRequest request,
                         @RequestParam("image") MultipartFile image) throws Exception {
        // 存储文件
        if (image == null || image.getOriginalFilename() == null) {
            return null;
        }
        LOG.info("上传文件: " + image.getOriginalFilename());

        String filename = generateFilename(image.getOriginalFilename());
        File file = new File(userFileBaseDir, filename);
        if (!file.getParentFile().exists()) {
            boolean mkdirFlag = file.getParentFile().mkdirs();
            if (!mkdirFlag) {
                throw new Exception("无法创建父文件夹");
            }
        }

        image.transferTo(file);

        // 获取当前服务端的IP地址，从而获取图片所在的网络位置，返回给前端
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        int serverPort = request.getServerPort();
        String protocol = request.getScheme();

        return protocol + "://" + hostAddress + ":" + serverPort
                + "/file/view/" + filename;
    }

    private String generateFilename(String originalFilename) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS");
        String formatDate = simpleDateFormat.format(date);
        String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (StringUtils.isEmpty(suffix)) {
            return filename + "-" + formatDate;
        } else {
            return filename + "-" + formatDate + "." + suffix;
        }
    }

    @GetMapping("/download/{filename}")
    public String fileDownLoad(HttpServletResponse response, @PathVariable("filename") String fileName)
            throws UnsupportedEncodingException {

        LOG.info("下载文件：{}", fileName);
        File file = new File(userFileBaseDir + fileName);
        if (!file.exists()) {
            return "下载文件不存在";
        }
        String tmpFileName = URLEncoder.encode(fileName, "utf-8");
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + tmpFileName);

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            LOG.error("下载失败", e);
            return "下载失败";
        }
        return "下载成功";
    }
}
