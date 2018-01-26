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
/*     */ public class IntHashSet
/*     */   extends PrimitiveSetBase
/*     */ {
/*     */   protected int[] m_keyTable;
/*     */   
/*     */   public IntHashSet(int paramInt, double paramDouble)
/*     */   {
/*  50 */     super(paramInt, paramDouble, Integer.TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntHashSet(int paramInt)
/*     */   {
/*  61 */     this(paramInt, 0.3D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntHashSet()
/*     */   {
/*  69 */     this(0, 0.3D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IntHashSet(IntHashSet paramIntHashSet)
/*     */   {
/*  79 */     super(paramIntHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object getKeyArray()
/*     */   {
/*  91 */     return this.m_keyTable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setKeyArray(Object paramObject)
/*     */   {
/* 103 */     this.m_keyTable = ((int[])paramObject);
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
/*     */   protected final boolean reinsert(int paramInt)
/*     */   {
/* 117 */     this.m_flagTable[paramInt] = false;
/* 118 */     return assignSlot(this.m_keyTable[paramInt]) != paramInt;
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
/*     */   protected void restructure(boolean[] paramArrayOfBoolean, Object paramObject)
/*     */   {
/* 133 */     int[] arrayOfInt = (int[])paramObject;
/* 134 */     for (int i = 0; i < paramArrayOfBoolean.length; i++) {
/* 135 */       if (paramArrayOfBoolean[i] != false) {
/* 136 */         assignSlot(arrayOfInt[i]);
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
/*     */   protected final int computeSlot(int paramInt)
/*     */   {
/* 149 */     return (paramInt * 517 & 0x7FFFFFFF) % this.m_flagTable.length;
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
/*     */   protected int assignSlot(int paramInt)
/*     */   {
/* 164 */     int i = freeSlot(computeSlot(paramInt));
/* 165 */     this.m_flagTable[i] = true;
/* 166 */     this.m_keyTable[i] = paramInt;
/* 167 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean add(int paramInt)
/*     */   {
/* 179 */     ensureCapacity(this.m_entryCount + 1);
/* 180 */     int i = -internalFind(paramInt) - 1;
/* 181 */     if (i >= 0) {
/* 182 */       this.m_entryCount += 1;
/* 183 */       this.m_flagTable[i] = true;
/* 184 */       this.m_keyTable[i] = paramInt;
/* 185 */       return true;
/*     */     }
/* 187 */     return false;
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
/*     */   protected final int internalFind(int paramInt)
/*     */   {
/* 200 */     int i = computeSlot(paramInt);
/* 201 */     while (this.m_flagTable[i] != false) {
/* 202 */       if (paramInt == this.m_keyTable[i]) {
/* 203 */         return i;
/*     */       }
/* 205 */       i = stepSlot(i);
/*     */     }
/* 207 */     return -i - 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(int paramInt)
/*     */   {
/* 219 */     return internalFind(paramInt) >= 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(int paramInt)
/*     */   {
/* 231 */     int i = internalFind(paramInt);
/* 232 */     if (i >= 0) {
/* 233 */       this.m_flagTable[i] = false;
/* 234 */       this.m_entryCount -= 1;
/* 235 */       while (this.m_flagTable[(i = stepSlot(i))] != false) {
/* 236 */         reinsert(i);
/*     */       }
/* 238 */       return true;
/*     */     }
/* 240 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 251 */     return new IntHashSet(this);
/*     */   }
/*     */ }


