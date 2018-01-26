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
/*     */ public abstract class ArrayBase
/*     */   extends GrowableBase
/*     */ {
/*     */   protected int m_countPresent;
/*     */   
/*     */   public ArrayBase(int paramInt1, int paramInt2, Class paramClass)
/*     */   {
/*  62 */     super(paramInt1, paramInt2, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayBase(int paramInt, Class paramClass)
/*     */   {
/*  73 */     this(paramInt, Integer.MAX_VALUE, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayBase(ArrayBase paramArrayBase)
/*     */   {
/*  83 */     super(paramArrayBase);
/*  84 */     System.arraycopy(paramArrayBase.getArray(), 0, getArray(), 0, paramArrayBase.m_countPresent);
/*     */     
/*  86 */     this.m_countPresent = paramArrayBase.m_countPresent;
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
/*     */   protected static Object getArray(ArrayBase paramArrayBase)
/*     */   {
/*  99 */     return paramArrayBase.getArray();
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
/*     */   protected final int getAddIndex()
/*     */   {
/* 112 */     int i = this.m_countPresent++;
/* 113 */     if (this.m_countPresent > this.m_countLimit) {
/* 114 */       growArray(this.m_countPresent);
/*     */     }
/* 116 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void makeInsertSpace(int paramInt)
/*     */   {
/* 126 */     if ((paramInt >= 0) && (paramInt <= this.m_countPresent)) {
/* 127 */       if (++this.m_countPresent > this.m_countLimit) {
/* 128 */         growArray(this.m_countPresent);
/*     */       }
/* 130 */       if (paramInt < this.m_countPresent - 1) {
/* 131 */         Object localObject = getArray();
/* 132 */         System.arraycopy(localObject, paramInt, localObject, paramInt + 1, this.m_countPresent - paramInt - 1);
/*     */       }
/*     */     }
/*     */     else {
/* 136 */       throw new ArrayIndexOutOfBoundsException("Invalid index value");
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
/*     */   public void remove(int paramInt1, int paramInt2)
/*     */   {
/* 149 */     if ((paramInt1 >= 0) && (paramInt2 <= this.m_countPresent) && (paramInt1 <= paramInt2)) {
/* 150 */       if (paramInt2 < this.m_countPresent) {
/* 151 */         int i = paramInt1 - paramInt2;
/* 152 */         Object localObject = getArray();
/* 153 */         System.arraycopy(localObject, paramInt2, localObject, paramInt1, this.m_countPresent - paramInt2);
/* 154 */         discardValues(this.m_countPresent + i, this.m_countPresent);
/* 155 */         this.m_countPresent += i;
/*     */       }
/*     */     } else {
/* 158 */       throw new ArrayIndexOutOfBoundsException("Invalid remove range");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(int paramInt)
/*     */   {
/* 170 */     remove(paramInt, paramInt + 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int size()
/*     */   {
/* 180 */     return this.m_countPresent;
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
/*     */   public void setSize(int paramInt)
/*     */   {
/* 193 */     if (paramInt > this.m_countLimit) {
/* 194 */       growArray(paramInt);
/* 195 */     } else if (paramInt < this.m_countPresent) {
/* 196 */       discardValues(paramInt, this.m_countPresent);
/*     */     }
/* 198 */     this.m_countPresent = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void clear()
/*     */   {
/* 206 */     setSize(0);
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
/*     */ 
/*     */   protected Object buildArray(Class paramClass, int paramInt1, int paramInt2)
/*     */   {
/* 222 */     if (paramInt1 + paramInt2 <= this.m_countPresent) {
/* 223 */       return super.buildArray(paramClass, paramInt1, paramInt2);
/*     */     }
/* 225 */     throw new ArrayIndexOutOfBoundsException();
/*     */   }
/*     */ }


