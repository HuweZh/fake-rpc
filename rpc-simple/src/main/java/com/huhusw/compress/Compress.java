package com.huhusw.compress;


import com.huhusw.extension.SPI;

/**
 * 压缩文件的各式
 */
@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}

