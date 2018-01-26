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
/*     */ public class DoubleArray
/*     */   extends ArrayBase
/*     */ {
/*     */   protected double[] m_baseArray;
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
/*     */   public DoubleArray(int paramInt1, int paramInt2)
/*     */   {
/*  51 */     super(paramInt1, paramInt2, Double.TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleArray(int paramInt)
/*     */   {
/*  62 */     super(paramInt, Double.TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleArray()
/*     */   {
/*  70 */     this(8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleArray(DoubleArray paramDoubleArray)
/*     */   {
/*  80 */     super(paramDoubleArray);
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
/*  91 */     return this.m_baseArray;
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
/* 102 */     this.m_baseArray = ((double[])paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int add(double paramDouble)
/*     */   {
/* 113 */     int i = getAddIndex();
/* 114 */     this.m_baseArray[i] = paramDouble;
/* 115 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(int paramInt, double paramDouble)
/*     */   {
/* 126 */     makeInsertSpace(paramInt);
/* 127 */     this.m_baseArray[paramInt] = paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final double get(int paramInt)
/*     */   {
/* 138 */     if (paramInt < this.m_countPresent) {
/* 139 */       return this.m_baseArray[paramInt];
/*     */     }
/* 141 */     throw new ArrayIndexOutOfBoundsException("Invalid index value");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void set(int paramInt, double paramDouble)
/*     */   {
/* 153 */     if (paramInt < this.m_countPresent) {
/* 154 */       this.m_baseArray[paramInt] = paramDouble;
/*     */     } else {
/* 156 */       throw new ArrayIndexOutOfBoundsException("Invalid index value");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double[] toArray()
/*     */   {
/* 168 */     return (double[])buildArray(Double.TYPE, 0, this.m_countPresent);
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
/*     */   public double[] toArray(int paramInt1, int paramInt2)
/*     */   {
/* 181 */     return (double[])buildArray(Double.TYPE, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 191 */     return new DoubleArray(this);
/*     */   }
/*     */ }


