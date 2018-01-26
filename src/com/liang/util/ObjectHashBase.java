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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObjectHashBase
/*     */ {
/*     */   public static final String STANDARD_HASH = "base";
/*     */   public static final String IDENTITY_COMP = "comp";
/*     */   public static final String IDENTITY_HASH = "ident";
/*     */   protected static final double DEFAULT_FILL = 0.3D;
/*     */   protected static final int MINIMUM_SIZE = 31;
/*     */   protected final boolean m_identHash;
/*     */   protected final boolean m_identCompare;
/*     */   protected final double m_fillFraction;
/*     */   protected int m_entryCount;
/*     */   protected int m_entryLimit;
/*     */   protected int m_arraySize;
/*     */   protected int m_hitOffset;
/*     */   
/*     */   public ObjectHashBase(int paramInt, double paramDouble, Class paramClass, Object paramObject)
/*     */   {
/* 112 */     if ((paramDouble <= 0.0D) || (paramDouble >= 1.0D)) {
/* 113 */       throw new IllegalArgumentException("fill value out of range");
/*     */     }
/* 115 */     this.m_fillFraction = paramDouble;
/*     */     
/*     */ 
/* 118 */     if (paramObject == "base") {
/* 119 */       this.m_identHash = false;
/* 120 */       this.m_identCompare = false;
/* 121 */     } else if (paramObject == "comp") {
/* 122 */       this.m_identHash = false;
/* 123 */       this.m_identCompare = true;
/* 124 */     } else if (paramObject == "ident") {
/* 125 */       this.m_identHash = true;
/* 126 */       this.m_identCompare = true;
/*     */     } else {
/* 128 */       throw new IllegalArgumentException("Unknown hash technique specifier");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 133 */     this.m_arraySize = Math.max((int)(paramInt / this.m_fillFraction), 31);
/* 134 */     this.m_arraySize += (this.m_arraySize + 1) % 2;
/*     */     
/*     */ 
/* 137 */     this.m_entryLimit = ((int)(this.m_arraySize * this.m_fillFraction));
/* 138 */     this.m_hitOffset = (this.m_arraySize / 2);
/* 139 */     setKeyArray(Array.newInstance(paramClass, this.m_arraySize));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectHashBase(ObjectHashBase paramObjectHashBase)
/*     */   {
/* 151 */     this.m_fillFraction = paramObjectHashBase.m_fillFraction;
/* 152 */     this.m_entryCount = paramObjectHashBase.m_entryCount;
/* 153 */     this.m_entryLimit = paramObjectHashBase.m_entryLimit;
/* 154 */     this.m_arraySize = paramObjectHashBase.m_arraySize;
/* 155 */     this.m_hitOffset = paramObjectHashBase.m_hitOffset;
/* 156 */     this.m_identHash = paramObjectHashBase.m_identHash;
/* 157 */     this.m_identCompare = paramObjectHashBase.m_identCompare;
/*     */     
/*     */ 
/* 160 */     Class localClass = paramObjectHashBase.getKeyArray().getClass().getComponentType();
/* 161 */     Object localObject = Array.newInstance(localClass, this.m_arraySize);
/* 162 */     System.arraycopy(paramObjectHashBase.getKeyArray(), 0, localObject, 0, this.m_arraySize);
/* 163 */     setKeyArray(localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int size()
/*     */   {
/* 173 */     return this.m_entryCount;
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
/*     */   protected abstract Object[] getKeyArray();
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
/* 217 */     int i = this.m_arraySize;
/* 218 */     int j = this.m_entryLimit;
/* 219 */     while (j < paramInt) {
/* 220 */       i = i * 2 + 1;
/* 221 */       j = (int)(i * this.m_fillFraction);
/*     */     }
/*     */     
/*     */ 
/* 225 */     this.m_arraySize = i;
/* 226 */     this.m_entryLimit = j;
/* 227 */     this.m_hitOffset = (i / 2);
/*     */     
/*     */ 
/* 230 */     reallocate(i);
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
/* 241 */     if (paramInt > this.m_entryLimit) {
/* 242 */       growCapacity(paramInt);
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
/* 253 */     Object[] arrayOfObject = getKeyArray();
/* 254 */     for (int i = 0; i < this.m_arraySize; i++) {
/* 255 */       arrayOfObject[i] = null;
/*     */     }
/* 257 */     this.m_entryCount = 0;
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
/* 269 */     return (paramInt + this.m_hitOffset) % this.m_arraySize;
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
/* 283 */     Object[] arrayOfObject = getKeyArray();
/* 284 */     while (arrayOfObject[paramInt] != null) {
/* 285 */       paramInt = stepSlot(paramInt);
/*     */     }
/* 287 */     return paramInt;
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
/*     */ 
/*     */ 
/*     */   protected final int standardSlot(Object paramObject)
/*     */   {
/* 305 */     return ((this.m_identHash ? System.identityHashCode(paramObject) : paramObject.hashCode()) & 0x7FFFFFFF) % this.m_arraySize;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int standardFind(Object paramObject)
/*     */   {
/* 328 */     int i = standardSlot(paramObject);
/*     */     
/*     */ 
/* 331 */     Object[] arrayOfObject = getKeyArray();
/* 332 */     if (this.m_identCompare) {
/* 333 */       while (arrayOfObject[i] != null)
/*     */       {
/*     */ 
/* 336 */         if (arrayOfObject[i] == paramObject) {
/* 337 */           return i;
/*     */         }
/* 339 */         i = stepSlot(i);
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 344 */       while (arrayOfObject[i] != null)
/*     */       {
/*     */ 
/* 347 */         if (arrayOfObject[i].equals(paramObject)) {
/* 348 */           return i;
/*     */         }
/* 350 */         i = stepSlot(i);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 355 */     return -i - 1;
/*     */   }
/*     */ }


