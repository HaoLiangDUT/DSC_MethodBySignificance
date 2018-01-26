/*     */ package com.liang.util;
/*     */ 
/*     */ import java.lang.reflect.Array;

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
/*     */ public abstract class PrimitiveHashBase
/*     */ {
/*     */   protected static final double DEFAULT_FILL = 0.3D;
/*     */   protected static final int MINIMUM_SIZE = 31;
/*     */   protected static final int KEY_MULTIPLIER = 517;
/*     */   protected double m_fillFraction;
/*     */   protected int m_entryCount;
/*     */   protected int m_entryLimit;
/*     */   protected int m_hitOffset;
/*     */   protected boolean[] m_flagTable;
/*     */   
/*     */   public PrimitiveHashBase(int paramInt, double paramDouble, Class paramClass)
/*     */   {
/* 100 */     if ((paramDouble <= 0.0D) || (paramDouble >= 1.0D)) {
/* 101 */       throw new IllegalArgumentException("fill value out of range");
/*     */     }
/* 103 */     this.m_fillFraction = paramDouble;
/*     */     
/*     */ 
/* 106 */     int i = Math.max((int)(paramInt / this.m_fillFraction), 31);
/* 107 */     i += (i + 1) % 2;
/*     */     
/*     */ 
/* 110 */     this.m_entryLimit = ((int)(i * this.m_fillFraction));
/* 111 */     this.m_hitOffset = (i / 2);
/* 112 */     this.m_flagTable = new boolean[i];
/* 113 */     setKeyArray(Array.newInstance(paramClass, i));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PrimitiveHashBase(PrimitiveHashBase paramPrimitiveHashBase)
/*     */   {
/* 125 */     this.m_fillFraction = paramPrimitiveHashBase.m_fillFraction;
/* 126 */     this.m_entryCount = paramPrimitiveHashBase.m_entryCount;
/* 127 */     this.m_entryLimit = paramPrimitiveHashBase.m_entryLimit;
/* 128 */     this.m_hitOffset = paramPrimitiveHashBase.m_hitOffset;
/*     */     
/*     */ 
/* 131 */     int i = paramPrimitiveHashBase.m_flagTable.length;
/* 132 */     this.m_flagTable = new boolean[i];
/* 133 */     System.arraycopy(paramPrimitiveHashBase.m_flagTable, 0, this.m_flagTable, 0, this.m_flagTable.length);
/*     */     
/*     */ 
/*     */ 
/* 137 */     Class localClass = paramPrimitiveHashBase.getKeyArray().getClass().getComponentType();
/* 138 */     Object localObject = Array.newInstance(localClass, i);
/* 139 */     System.arraycopy(paramPrimitiveHashBase.getKeyArray(), 0, localObject, 0, i);
/* 140 */     setKeyArray(localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int size()
/*     */   {
/* 150 */     return this.m_entryCount;
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
/*     */   protected abstract Object getKeyArray();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void setKeyArray(Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void reallocate(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void growCapacity(int paramInt)
/*     */   {
/* 194 */     int i = this.m_flagTable.length;
/* 195 */     int j = this.m_entryLimit;
/* 196 */     while (j < paramInt) {
/* 197 */       i = i * 2 + 1;
/* 198 */       j = (int)(i * this.m_fillFraction);
/*     */     }
/*     */     
/*     */ 
/* 202 */     this.m_entryLimit = j;
/* 203 */     this.m_hitOffset = (i / 2);
/*     */     
/*     */ 
/* 206 */     reallocate(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void ensureCapacity(int paramInt)
/*     */   {
/* 217 */     if (paramInt > this.m_entryLimit) {
/* 218 */       growCapacity(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 229 */     for (int i = 0; i < this.m_flagTable.length; i++) {
/* 230 */       this.m_flagTable[i] = false;
/*     */     }
/* 232 */     this.m_entryCount = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final int stepSlot(int paramInt)
/*     */   {
/* 244 */     return (paramInt + this.m_hitOffset) % this.m_flagTable.length;
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
/*     */   protected final int freeSlot(int paramInt)
/*     */   {
/* 258 */     while (this.m_flagTable[paramInt] != false) {
/* 259 */       paramInt = stepSlot(paramInt);
/*     */     }
/* 261 */     return paramInt;
/*     */   }
/*     */ }

