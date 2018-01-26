/*     */ package com.liang.util;
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
/*     */ public class IntArray
/*     */   extends ArrayBase
/*     */ {
/*     */   protected int[] m_baseArray;
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
/*     */   public IntArray(int paramInt1, int paramInt2)
/*     */   {
/*  50 */     super(paramInt1, paramInt2, Integer.TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntArray(int paramInt)
/*     */   {
/*  60 */     super(paramInt, Integer.TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntArray()
/*     */   {
/*  68 */     this(8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntArray(IntArray paramIntArray)
/*     */   {
/*  78 */     super(paramIntArray);
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
/*  89 */     return this.m_baseArray;
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
/* 100 */     this.m_baseArray = ((int[])paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int add(int paramInt)
/*     */   {
/* 111 */     int i = getAddIndex();
/* 112 */     this.m_baseArray[i] = paramInt;
/* 113 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(int paramInt1, int paramInt2)
/*     */   {
/* 124 */     makeInsertSpace(paramInt1);
/* 125 */     this.m_baseArray[paramInt1] = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int get(int paramInt)
/*     */   {
/* 136 */     if (paramInt < this.m_countPresent) {
/* 137 */       return this.m_baseArray[paramInt];
/*     */     }
/* 139 */     throw new ArrayIndexOutOfBoundsException("Invalid index value");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(int paramInt1, int paramInt2)
/*     */   {
/* 151 */     if (paramInt1 < this.m_countPresent) {
/* 152 */       this.m_baseArray[paramInt1] = paramInt2;
/*     */     } else {
/* 154 */       throw new ArrayIndexOutOfBoundsException("Invalid index value");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] toArray()
/*     */   {
/* 166 */     return (int[])buildArray(Integer.TYPE, 0, this.m_countPresent);
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
/*     */   public int[] toArray(int paramInt1, int paramInt2)
/*     */   {
/* 179 */     return (int[])buildArray(Integer.TYPE, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 189 */     return new IntArray(this);
/*     */   }
/*     */ }


