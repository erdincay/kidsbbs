// Copyright (c) 2010, Younghong "Hong" Cho <hongcho@sori.org>.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
//   1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//   2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//   3. Neither the name of the organization nor the names of its contributors
// may be used to endorse or promote products derived from this software
// without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
// THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
package org.sori.kidsbbs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleInfo {
	static final private String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	static final private String DATESHORT1_FORMAT = "HH:mm";
	static final private String DATESHORT2_FORMAT = "yyyy-MM-dd";
	static final private String DATE_INVALID = "0000-00-00 00:00";
	static final private String DATESHORT_INVALID = "0000-00-00";
	
	static final private Pattern PATTERN_SPACE= Pattern.compile("&nbsp;");
	static final private Pattern PATTERN_NEWLINE = Pattern.compile("<br/>");
	
	private int mSeq;
	private String mUsername;
	private String mTitle;
	private String mThread;
	private String mBody;
	private int mCount;
	private String mDateString;
	private String mDateShortString;
	
	public int getSeq() { return mSeq; }
	public String getUsername() { return mUsername; }
	public String getTitle() { return mTitle; }
	public String getThread() { return mThread; }
	public String getBody() { return mBody; }
	public int getCount() { return mCount; }
	public String getDateString() { return mDateString; }
	public String getDateShortString() { return mDateShortString; }
	
	public ArticleInfo(int _seq, String _username, String _dateString,
			String _title, String _thread, String _body, int _count) {
		mSeq = _seq;
		mUsername = _username;
		mTitle = _title;
		mThread = _thread;
		mCount = _count;
		
		Calendar calLocal = new GregorianCalendar();
		DateFormat dfKorea = new SimpleDateFormat(DATE_FORMAT);
		dfKorea.setTimeZone(TimeZone.getTimeZone("Korea"));
		DateFormat dfLong = new SimpleDateFormat(DATE_FORMAT);
		dfLong.setTimeZone(TimeZone.getDefault());
		DateFormat dfShort1 = new SimpleDateFormat(DATESHORT1_FORMAT);
		dfShort1.setTimeZone(TimeZone.getDefault());
		DateFormat dfShort2 = new SimpleDateFormat(DATESHORT2_FORMAT);
		dfShort2.setTimeZone(TimeZone.getDefault());
		try {
			Date date = dfKorea.parse(_dateString);
			mDateString = dfLong.format(date);
			
			calLocal.setTime(new Date());
			int dateNow = calLocal.get(Calendar.DATE);
			calLocal.setTime(date);
			int dateA = calLocal.get(Calendar.DATE);

			if (dateNow == dateA) {
				mDateShortString = dfShort1.format(date);
			} else {
				mDateShortString = dfShort2.format(date);
			}
		} catch (ParseException e) {
			mDateString = DATE_INVALID;
			mDateShortString = DATESHORT_INVALID;
		}
		
		Matcher m1 = PATTERN_SPACE.matcher(_body);
		_body = m1.replaceAll(" ");
		Matcher m2 = PATTERN_NEWLINE.matcher(_body);
		mBody = m2.replaceAll("\n");
	}
}
