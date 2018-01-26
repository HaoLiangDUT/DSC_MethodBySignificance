/*     */ package com.liang.util;
/*     */ 
/*     */


import java.util.Iterator;

/*     */

/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringArray
/*     */   extends ArrayBase
/*     */ {
/*     */   protected String[] m_baseArray;
/*     */   
/*     */   public StringArray(int paramInt1, int paramInt2)
/*     */   {
/*  55 */     super(paramInt1, paramInt2, String.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringArray(int paramInt)
/*     */   {
/*  65 */     super(paramInt, String.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringArray()
/*     */   {
/*  73 */     this(8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringArray(StringArray paramStringArray)
/*     */   {
/*  83 */     super(paramStringArray);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Object getArray()
/*     */   {
/*  94 */     return this.m_baseArray;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void setArray(Object paramObject)
/*     */   {
/* 105 */     this.m_baseArray = ((String[])paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int add(String paramString)
/*     */   {
/* 116 */     int i = getAddIndex();
/* 117 */     this.m_baseArray[i] = paramString;
/* 118 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(int paramInt, String paramString)
/*     */   {
/* 129 */     makeInsertSpace(paramInt);
/* 130 */     this.m_baseArray[paramInt] = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String get(int paramInt)
/*     */   {
/* 141 */     if (paramInt < this.m_countPresent) {
/* 142 */       return this.m_baseArray[paramInt];
/*     */     }
/* 144 */     throw new ArrayIndexOutOfBoundsException("Invalid index value");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(int paramInt, String paramString)
/*     */   {
/* 159 */     if (paramInt < this.m_countPresent) {
/* 160 */       this.m_baseArray[paramInt] = paramString;
/*     */     } else {
/* 162 */       throw new ArrayIndexOutOfBoundsException("Invalid index value");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Iterator iterator()
/*     */   {
/* 177 */     return ArrayRangeIterator.buildIterator(this.m_baseArray, 0, this.m_countPresent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] toArray()
/*     */   {
/* 188 */     return (String[])buildArray(String.class, 0, this.m_countPresent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] toArray(int paramInt1, int paramInt2)
/*     */   {
/* 201 */     return (String[])buildArray(String.class, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 211 */     return new StringArray(this);
/*     */   }
/*     */ }


