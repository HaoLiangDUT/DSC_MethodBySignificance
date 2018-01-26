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
/*     */ public abstract class GrowableBase
/*     */ {
/*     */   public static final int DEFAULT_SIZE = 8;
/*     */   protected int m_countLimit;
/*     */   protected int m_maximumGrowth;
/*     */   
/*     */   public GrowableBase(int paramInt1, int paramInt2, Class paramClass)
/*     */   {
/*  60 */     Object localObject = Array.newInstance(paramClass, paramInt1);
/*  61 */     this.m_countLimit = paramInt1;
/*  62 */     this.m_maximumGrowth = paramInt2;
/*  63 */     setArray(localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GrowableBase(int paramInt, Class paramClass)
/*     */   {
/*  74 */     this(paramInt, Integer.MAX_VALUE, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GrowableBase(GrowableBase paramGrowableBase)
/*     */   {
/*  84 */     this(paramGrowableBase.m_countLimit, paramGrowableBase.m_maximumGrowth, paramGrowableBase.getArray().getClass().getComponentType());
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
/*     */   protected abstract Object getArray();
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
/*     */   protected abstract void setArray(Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void resizeCopy(Object paramObject1, Object paramObject2)
/*     */   {
/* 119 */     System.arraycopy(paramObject1, 0, paramObject2, 0, Array.getLength(paramObject1));
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
/*     */   protected void discardValues(int paramInt1, int paramInt2)
/*     */   {
/* 132 */     Object localObject = getArray();
/* 133 */     if (!localObject.getClass().getComponentType().isPrimitive()) {
/* 134 */       Object[] arrayOfObject = (Object[])localObject;
/* 135 */       for (int i = paramInt1; i < paramInt2; i++) {
/* 136 */         arrayOfObject[i] = null;
/*     */       }
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
/*     */ 
/*     */ 
/*     */   protected void growArray(int paramInt)
/*     */   {
/* 154 */     Object localObject1 = getArray();
/* 155 */     int i = Math.max(paramInt, this.m_countLimit + Math.min(this.m_countLimit, this.m_maximumGrowth));
/*     */     
/* 157 */     Class localClass = localObject1.getClass().getComponentType();
/* 158 */     Object localObject2 = Array.newInstance(localClass, i);
/* 159 */     resizeCopy(localObject1, localObject2);
/* 160 */     this.m_countLimit = i;
/* 161 */     setArray(localObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void  ensureCapacity(int paramInt)
/*     */   {
/* 172 */     if (paramInt > this.m_countLimit) {
/* 173 */       growArray(paramInt);
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
/*     */   protected Object buildArray(Class paramClass, int paramInt1, int paramInt2)
/*     */   {
/* 188 */     Object localObject = Array.newInstance(paramClass, paramInt2);
/* 189 */     System.arraycopy(getArray(), paramInt1, localObject, 0, paramInt2);
/* 190 */     return localObject;
/*     */   }
/*     */ }

