package lengj.interfacedata.demo.util;

import lengj.interfacedata.demo.io.MyByteArrayInputStream;
import lengj.interfacedata.demo.io.MyByteArrayOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



public final class StmFunc {
  public static final byte[] NULLBYTE = new byte[0];
  
  private StmFunc() {
  }
  
  /**
   * 将对象obj序列化到输出流o中，obj必须是支持序列化的
   */
  public static void writeObject(OutputStream o, Object obj) throws IOException {
    ObjectOutputStream oo = new ObjectOutputStream(o);
    try {
      oo.writeObject(obj);
    }
    finally {
      oo.close();
    }
  }
  
  public static Object readObject(InputStream o) throws IOException, ClassNotFoundException {
    ObjectInputStream oo = new ObjectInputStream(o);
    try {
      return oo.readObject();
    }
    finally {
      oo.close();
    }
  }
  
  public static Object stm2obj(InputStream f) throws IOException, ClassNotFoundException {
    ObjectInputStream oo = new ObjectInputStream(f);
    try{
      return oo.readObject();
    }finally{
      oo.close();
    }
  }

	/**
	 * read fix length bytes from inputstream
	 * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
	 * @param in
	 * @param fix
	 * @return
	 * @throws IOException
	 */
	static public final String readFix(InputStream in, int fix) throws IOException {
		if (fix <= 0)
			return null;
		byte[] bb = new byte[fix];
		stmTryRead(in, bb, fix);
		return new String(bb);
	}
	
	/**
	 * read fix length bytes from inputstream
	 * @param in
	 * @param fix
	 * @return
	 * @throws IOException
	 */
	static public final String readFix(InputStream in, int fix, String charset) throws IOException {
		if (fix <= 0)
			return null;
		byte[] bb = new byte[fix];
		stmTryRead(in, bb, fix);
		return new String(bb, charset);
	}
	
  /**
   * 从流中读取一行,采用操作系统默认编码对字节编码
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
   * @param in
   * @return
   * @throws IOException
   */
  static public final String readLine(InputStream in) throws IOException {
    return readLine(in, null);
  }
  
  /**
   * 从流中读取一行
   * @param in
   * @param charset 以charset对编码为字符串,如果为空,则采用操作系统默认编码
   * @return
   * @throws IOException
   */
  static public final String readLine(InputStream in,String charset) throws IOException {
    int rd = in.read();
    if (rd == -1)
      return null;
    byte r = (byte) rd;
    int i = 0;
    int l = 50;
    byte[] buf = new byte[l];
    while (r != '\n') {
      if (i >= l - 1) {
        l += 50;
        byte[] old = buf;
        buf = new byte[l];
        System.arraycopy(old, 0, buf, 0, old.length);
      }
      if (r != '\r')
        buf[i++] = r;
      rd = in.read();
      if (rd == -1)
        break;
      r = (byte) rd;
    }
    if (charset == null) {
      return new String(buf, 0, i);
    }
    return new String(buf, 0, i, charset);
  }
  
  /**
   * 向流里写一行，即以回车换行为结尾
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
   * @param out
   * @param s
   * @throws IOException
   */
  static public final void writeLine(OutputStream out, String s)
      throws IOException {
    writeFix(out, s);
    out.write('\r');
    out.write('\n');
  }

  /**
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
 * @param out
 * @param s
 * @throws IOException
 */
  static public final void writeFix(OutputStream out, String s)
      throws IOException {
    if (s == null || s.length() == 0)
      return;
    byte[] b = s.getBytes();
    out.write(b);
  }
  
  static public final void writeLine(OutputStream out, String s, String charsetName) throws IOException {
    writeFix(out, s, charsetName);
    out.write('\r');
    out.write('\n');
  }
  
  /**
   * 按指定编码写字符串的内容,如果编码为null这使用系统缺省编码
   */
  static public final void writeFix(OutputStream out, String s, String charsetName)
      throws IOException {
    if (s == null || s.length() == 0)
      return;
    byte[] b = charsetName!=null?s.getBytes(charsetName):s.getBytes();
    out.write(b);
  }
  
  //write fix length string to outputstream
  static public final void writeFix(OutputStream out, String s, int fix)
      throws IOException {
    if (fix <= 0)
      return;
    byte[] b = s.getBytes();
    if (b.length < fix)
      throw new IOException();
    out.write(b, 0, fix);
  }

  /**
   * 读取行，直到读到某行为止,以操作系统默认编码对字节编码
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
   * @param br
   * @param ln
   * @return
   * @throws Exception
   */
  static public final String readLinesUntil(InputStream br, String ln) throws Exception {
    return readLinesUntil(br, ln, null);
  }
  
  /**
   * 读取行，直到读到某行为止
   * 以指定编码解码,如果为空,则以系统默认编码解码
   * 此问题主要是解决读取npf文件时必须以gbk解码的问题
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
   * @param br
   * @param ln
   * @return
   * @throws Exception
   */
  static public final String readLinesUntil(InputStream br, String ln, String charset) throws Exception {
    StringBuffer result = new StringBuffer();
    String s = readLine(br, charset);
    while ((s != null) && !s.equals(ln)) {
      result.append(s);
      s = readLine(br, charset);
      if ((s != null) && !s.equals(ln))
        result.append("\r\n");
    }
    return result.toString();
  }

  /**
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
   * @param br
   * @param ln
   * @throws Exception
   */
  static public final void skipLinesUntil(InputStream br, String ln)
      throws Exception {
    String s = readLine(br);
    while ((s != null) && !s.equals(ln)) {
      s = readLine(br);
    }
  }

  static public final int readInt(InputStream i) throws IOException,
      EOFException {
    InputStream in = i;
    int ch1 = in.read();
    int ch2 = in.read();
    int ch3 = in.read();
    int ch4 = in.read();
    if ((ch1 | ch2 | ch3 | ch4) < 0)
      throw new EOFException();
    return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
  }

  static public final long readLong(InputStream i) throws IOException {
    return ((long) (readInt(i)) << 32) + (readInt(i) & 0xFFFFFFFFL);
  }

  static public final double readDouble(InputStream i) throws IOException {
    return Double.longBitsToDouble(readLong(i));
  }

  static public final float readFloat(InputStream i) throws IOException {
    return Float.intBitsToFloat(readInt(i));
  }

  static public final void writeDouble(OutputStream o, double v)
      throws IOException {
    writeLong(o, Double.doubleToLongBits(v));
  }

  static public final void writeInt(OutputStream o, int v) throws IOException {
    OutputStream out = o;
    out.write((v >>> 24) & 0xFF);
    out.write((v >>> 16) & 0xFF);
    out.write((v >>> 8) & 0xFF);
    out.write((v >>> 0) & 0xFF);
  }

  static public final void writeFloat(OutputStream o, float v)
      throws IOException {
    writeInt(o, Float.floatToIntBits(v));
  }

  static public final void writeLong(OutputStream o, long v) throws IOException {
    OutputStream out = o;
    out.write((int) (v >>> 56) & 0xFF);
    out.write((int) (v >>> 48) & 0xFF);
    out.write((int) (v >>> 40) & 0xFF);
    out.write((int) (v >>> 32) & 0xFF);
    out.write((int) (v >>> 24) & 0xFF);
    out.write((int) (v >>> 16) & 0xFF);
    out.write((int) (v >>> 8) & 0xFF);
    out.write((int) (v >>> 0) & 0xFF);
  }
  
  /**
   * 将文件写入流
   * @param o
   * @param file
   * @throws IOException
   */
  static public final void writeFileContent(OutputStream o, File file) throws IOException {
  	if ((o == null) || (file == null)) {
  		return;
  	}
  	FileInputStream fis = new FileInputStream(file);
  	try {
  		BufferedInputStream bis = new BufferedInputStream(fis);
  		try {
  			stmTryCopyFrom(bis, o);
  		} finally {
  			bis.close();
  		}
  	} finally {
  		fis.close();
  	}
  }
  
  /**
   * 从输入流读取数据，写入文件
   * @param istm
   * @param file
   * @throws IOException
   */
  static public final void readFileContent(InputStream istm, File file) throws IOException {
  	if ((istm == null) || (file == null)) {
  		return;
  	}
  	FileOutputStream fos = new FileOutputStream(file);
  	try {
  		BufferedOutputStream bos = new BufferedOutputStream(fos);
  		try {
  			stmTryCopyFrom(istm, bos);
  			bos.flush();
  		} finally {
  			bos.close();
  		}
  	} finally {
  		fos.close();
  	}
  }

  static public final int stmCopyFrom(String fn, OutputStream out)
      throws IOException {
    InputStream in = new FileInputStream(fn);
    try {
      return stmTryCopyFrom(in, out);
    }
    finally {
      in.close();
    }
  }

  /**
   * 从一个流中复制指定的长度的类容到另一个流中,如果从源流中不能再读入数据则返回复制了的数据的字节数
   */
  static private final int BUF_SIZE = 1024 * 8;

  static public final long stmCopyFrom(InputStream in, OutputStream out, long sz)
      throws IOException {
    byte[] buf = new byte[BUF_SIZE];
    long rst = 0;
    int r;
    while (sz > 0) {
      r = (int) (sz > BUF_SIZE ? BUF_SIZE : sz);
      r = in.read(buf, 0, r);
      if (r < 1)
        break;
      sz -= r;     
      out.write(buf, 0, r);
      rst += r;
    }
    return rst;
  }

  /**
   * 将流中的所有信息读出并以byte数组的形式返回
   */
  static public final byte[] stm2bytes(InputStream in) throws IOException {
    if (in instanceof MyByteArrayInputStream){
      MyByteArrayInputStream min = (MyByteArrayInputStream)in;
      byte[] r = new byte[min.available()];
      System.arraycopy(min.getBuf(),min.getPos(),r,0,r.length);
      return r;
    }
    int available = in.available();
    if (available<=0) available = 5*1024;
    MyByteArrayOutputStream out = new MyByteArrayOutputStream(available);
    stmTryCopyFrom(in, out);
    //如果out.getBuf().length==out.size()那么直接返回buf即可，不必再次复制内存块
    return out.getBuf().length==out.size()?out.getBuf():out.toByteArray();
  }

  /**
   * 将流中的所有信息读出并以字符串的形式返回
   * @deprecated 此函数使用系统的编码，不稳定，应该指定明确的编码
   * @param in
   * @return
   * @throws IOException
   */
  static public final String stm2Str(InputStream in) throws IOException {
    return new String(stm2bytes(in));
  }

  static public final String stm2Str(InputStream in, String charsetName) throws IOException {
    return new String(stm2bytes(in), charsetName);
  }

  static public final int stmTryCopyFrom(InputStream in, OutputStream out)
      throws IOException {
    if (in instanceof MyByteArrayInputStream){
      MyByteArrayInputStream min = (MyByteArrayInputStream)in;
      out.write(min.getBuf(), min.getPos(), min.available());
      return min.available();
    }
    byte[] buf = new byte[BUF_SIZE];
    int sz = 0;
    int r;
    while ((r = in.read(buf)) != -1) {
      sz += r;
      out.write(buf, 0, r);
    }
    return sz;
  }
  
  /**
   * 将流reader的内容拷贝到writer中
   * @param reader
   * @param writer
   * @return
   * @throws IOException
   */
  static public final int stmTryCopyFrom(Reader reader, Writer writer) throws IOException {
    char[] buffer = new char[BUF_SIZE];
    int mark = 0;
    int size = 0;
    while ((mark = reader.read(buffer)) != -1) {
      size += mark;
      writer.write(buffer, 0, mark);
    }
    return size;
  }
  
  static public final long stmTryCopyFrom(InputStream in, RandomAccessFile out) throws IOException {
    if (in instanceof MyByteArrayInputStream) {
      MyByteArrayInputStream min = (MyByteArrayInputStream) in;
      out.write(min.getBuf(), min.getPos(), min.available());
      return min.available();
    }
    byte[] buf = new byte[BUF_SIZE];
    long sz = 0;
    int r;
    while ((r = in.read(buf)) != -1) {
      sz += r;
      out.write(buf, 0, r);
    }
    return sz;
  }
  /**
   * 判断给定的流是否是gzip格式的流，如果in是markSupported的，那么此函数不会改变in的当前位置。
   */
  static public final boolean isGzipStm(InputStream in) throws IOException{
    boolean ms = in.markSupported();
    if (ms) in.mark(10);
    int b1 = in.read();
    int b2 = in.read();
    if (ms) in.reset();
    return ((b2 << 8 | b1) == GZIPInputStream.GZIP_MAGIC);
  }

  /**
   * 将bytes用gzip压缩并返回压缩后的byte，byte如果是null则触发空指针异常
   * @throws IOException 
   */
  static public final byte[] gzipBytes(byte[] bytes) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gout = new GZIPOutputStream(out);
    try {
      gout.write(bytes);
    }
    finally {
      gout.close();
    }
    return out.toByteArray();
  }

  /**
   * 将bytes用gzip解压缩并返回解压缩后的byte，byte如果是null则触发空指针异常
   * @throws IOException 
   */
  static public final byte[] ungzipBytes(byte[] bytes) throws IOException {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    GZIPInputStream gin = new GZIPInputStream(in);
    try {
      return stm2bytes(gin);
    }
    finally {
      gin.close();
    }
  }

  /**
   * 将in中的类容用gzip压缩并返回压缩后的byte，in如果是null则触发空指针异常
   * @throws IOException 
   */
  static public final byte[] gzipStm(InputStream in) throws IOException {
    MyByteArrayOutputStream out = new MyByteArrayOutputStream(
        in.available() > 1 ? in.available() : 1024);
    GZIPOutputStream gout = new GZIPOutputStream(out);
    try {
      StmFunc.stmTryCopyFrom(in, gout);
    }
    finally {
      gout.close();
    }
    return out.toByteArray();
  }

  /**
   * 将in中的类容用gzip解压缩并返回解压缩后的byte，in如果是null则触发空指针异常
   * @throws IOException 
   */
  static public final byte[] ungzipStm(InputStream in) throws IOException {
    return ungzipBytes(stm2bytes(in));
  }

  public static final int stmTryRead(InputStream in, byte[] bb)
      throws IOException {
    if ((in == null) || (bb == null) || (bb.length == 0))
      return 0;
    return stmTryRead(in, bb, bb.length);
  }

  public static final int stmTryRead(InputStream in, byte[] bb, int len)
      throws IOException {
    return stmTryRead(in, bb, 0, len);
  }

  public static int stmTryRead(InputStream in, byte[] bb, int off, int len)
      throws IOException {
    int r;
    int l = 0;
    int t = off;
    while ((r = in.read(bb, t, len - l)) >= 0) {
      t += r;
      l += r;
      if (l == len) {
        break;
      }
    }
    return l;
  }

  //reader
  public static Reader str2reader(String str) {
    if (str == null) {
      return null;
    }
    return new CharArrayReader(str.toCharArray());
  }
  public static String reader2str(Reader reader,boolean needCloseStm) throws IOException {
    if (reader == null) {
      return null;
    }
    return new String(reader2chars(reader,needCloseStm));
  }
  public static String reader2str(Reader reader) throws IOException {
    return reader2str(reader,true);
  }

  /**
   * 将Reader中的信息读出,并以char数组的形式返回
   * 
   * @param reader
   * @param needCloseStm 
   * @return
   * @throws IOException
   */
  public static char[] reader2chars(Reader reader, boolean needCloseStm) throws IOException {
    CharArrayWriter writer = new CharArrayWriter();
    reader2writer(reader, writer);
    if(needCloseStm)
      reader.close();
    return writer.toCharArray();
  }

  public static int reader2writer(Reader reader, Writer writer)
      throws IOException {
    char[] buf = new char[BUF_SIZE];
    int sz = 0;
    int r;
    while ((r = reader.read(buf)) != -1) {
      sz += r;
      writer.write(buf, 0, r);
    }
    return sz;
  }

  public static final int readerTryRead(Reader reader, char[] cc, int len)
      throws IOException {
    int r;
    int t = 0;
    while ((r = reader.read(cc, t, len - t)) >= 0) {
      t += r;
      if (t == len) {
        break;
      }
    }
    return t;
  }

  public static int readerTryRead(Reader reader, char[] cc) throws IOException {
    if (reader == null || cc == null || cc.length == 0) {
      return 0;
    }
    return readerTryRead(reader, cc, cc.length);
  }

	/**
	 * 跳过指定字节读取文件
	 * @param in 输入的流
	 * @param len 跳过的字节长度
	 * @return
	 * @throws IOException
	 */
  public static long skip(InputStream in, long len) throws IOException {
    if (len <= 0) {
      return 0;
    }
    long r;
    long t = 0;
    while ((r = in.skip(len - t)) != 0) {
      t += r;
      if (t == len) {
        break;
      }
    }
    return t;
  }


}
